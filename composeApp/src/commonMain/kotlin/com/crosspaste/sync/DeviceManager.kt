package com.crosspaste.sync

import com.crosspaste.app.AppInfo
import com.crosspaste.config.ConfigManager
import com.crosspaste.dto.sync.SyncInfo
import com.crosspaste.realm.sync.SyncRuntimeInfoRealm
import com.crosspaste.realm.sync.createSyncRuntimeInfo
import com.crosspaste.utils.getJsonUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DeviceManager(
    private val appInfo: AppInfo,
    private val configManager: ConfigManager,
    private val syncManager: SyncManager,
    private val syncRuntimeInfoRealm: SyncRuntimeInfoRealm,
) : DeviceListener {

    private val logger = KotlinLogging.logger {}

    private val jsonUtils = getJsonUtils()

    private val _searching = MutableStateFlow<Boolean>(false)

    val searching: StateFlow<Boolean> = _searching.asStateFlow()

    private val _allSyncInfos = MutableStateFlow<Map<String, SyncInfo>>(mapOf())

    private val _syncInfos = MutableStateFlow<List<SyncInfo>>(listOf())
    override val syncInfos = _syncInfos.asStateFlow()

    private val isSelf: (String) -> Boolean = { appInstanceId ->
        appInstanceId == appInfo.appInstanceId
    }

    private val isNew: (String) -> Boolean = { appInstanceId ->
        !syncManager.getSyncHandlers().keys.contains(appInstanceId)
    }

    private val isBlackListed: (String) -> Boolean = { appInstanceId ->
        val blackSyncInfos: List<SyncInfo> =
            jsonUtils.JSON.decodeFromString(
                configManager.config.blacklist,
            )
        blackSyncInfos.map { it.appInfo.appInstanceId }
            .contains(appInstanceId)
    }

    fun refresh() {
        _searching.value = true
        try {
            val currentAllSyncInfos = _allSyncInfos.value

            // Handle existing sync infos
            val existSyncRuntimeInfos =
                currentAllSyncInfos
                    .filter { !isNew(it.key) }
                    .map { createSyncRuntimeInfo(it.value) }

            if (existSyncRuntimeInfos.isNotEmpty()) {
                syncManager.refresh(syncRuntimeInfoRealm.update(existSyncRuntimeInfos))
            }

            // Update syncInfos with new, non-blacklisted devices
            val newSyncInfos =
                currentAllSyncInfos
                    .filter { isNew(it.key) && !isBlackListed(it.key) }
                    .values
                    .toList()

            _syncInfos.value = newSyncInfos
        } finally {
            _searching.value = false
        }
    }

    override fun addDevice(syncInfo: SyncInfo) {
        val appInstanceId = syncInfo.appInfo.appInstanceId
        logger.debug { "Service resolved: $syncInfo" }
        if (!isSelf(appInstanceId)) {
            _allSyncInfos.update { current ->
                current + (appInstanceId to syncInfo)
            }
            refresh()
        }
    }

    override fun removeDevice(syncInfo: SyncInfo) {
        val appInstanceId = syncInfo.appInfo.appInstanceId
        logger.debug { "Service removed: $syncInfo" }
        _allSyncInfos.update { current ->
            current - appInstanceId
        }
        refresh()
    }
}

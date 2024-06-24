package com.clipevery.clip.plugin

import com.clipevery.clip.ClipPlugin
import com.clipevery.clip.item.FilesClipItem
import com.clipevery.clip.item.ImagesClipItem
import com.clipevery.dao.clip.ClipItem
import com.clipevery.utils.DesktopJsonUtils
import com.clipevery.utils.getEncryptUtils
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.encodeToString

object MultiImagesPlugin : ClipPlugin {

    private val encryptUtils = getEncryptUtils()

    override fun pluginProcess(
        clipItems: List<ClipItem>,
        realm: MutableRealm,
    ): List<ClipItem> {
        if (clipItems.size <= 1) {
            return clipItems
        } else {
            val relativePathList =
                clipItems.map { it as ImagesClipItem }.flatMap { it.relativePathList }
                    .toRealmList()
            val fileInfoMap =
                clipItems.map { it as ImagesClipItem }
                    .flatMap { it.getFileInfoTreeMap().entries }
                    .associate { it.key to it.value }
            val fileInfoMapJsonString = DesktopJsonUtils.JSON.encodeToString(fileInfoMap)
            val count = fileInfoMap.map { it.value.getCount() }.sum()
            val size = fileInfoMap.map { it.value.size }.sum()
            val md5 =
                clipItems.map { it as FilesClipItem }.map { it.md5 }
                    .toTypedArray().let { encryptUtils.md5ByArray(it) }
            clipItems.forEach { it.clear(realm, clearResource = false) }
            return ImagesClipItem().apply {
                this.relativePathList = relativePathList
                this.fileInfoTree = fileInfoMapJsonString
                this.count = count
                this.size = size
                this.md5 = md5
            }.let { listOf(it) }
        }
    }
}

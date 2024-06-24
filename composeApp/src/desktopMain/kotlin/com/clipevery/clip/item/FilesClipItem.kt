package com.clipevery.clip.item

import com.clipevery.app.AppFileType
import com.clipevery.clip.LinuxClipboardService.Companion.GNOME_COPIED_FILES_FLAVOR
import com.clipevery.dao.clip.ClipItem
import com.clipevery.dao.clip.ClipState
import com.clipevery.dao.clip.ClipType
import com.clipevery.path.DesktopPathProvider
import com.clipevery.platform.currentPlatform
import com.clipevery.presist.DesktopOneFilePersist
import com.clipevery.presist.FileInfoTree
import com.clipevery.serializer.PathStringRealmListSerializer
import com.clipevery.serializer.StringRealmListSerializer
import com.clipevery.utils.DesktopJsonUtils
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.awt.datatransfer.DataFlavor
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths

@Serializable
@SerialName("files")
class FilesClipItem : RealmObject, ClipItem, ClipFiles {

    companion object {}

    @PrimaryKey
    @Transient
    override var id: ObjectId = BsonObjectId()

    @Serializable(with = StringRealmListSerializer::class)
    var identifiers: RealmList<String> = realmListOf()

    @Serializable(with = PathStringRealmListSerializer::class)
    var relativePathList: RealmList<String> = realmListOf()

    var fileInfoTree: String = ""

    @Index
    override var favorite: Boolean = false

    override var count: Long = 0L

    override var size: Long = 0L

    override var md5: String = ""

    @Index
    @Transient
    override var clipState: Int = ClipState.LOADING

    override var extraInfo: String? = null

    override fun getAppFileType(): AppFileType {
        return AppFileType.FILE
    }

    override fun getRelativePaths(): List<String> {
        return relativePathList
    }

    override fun getFilePaths(): List<Path> {
        val basePath = DesktopPathProvider.resolve(appFileType = getAppFileType())
        return relativePathList.map { relativePath ->
            DesktopPathProvider.resolve(basePath, relativePath, autoCreate = false, isFile = true)
        }
    }

    override fun getFileInfoTreeMap(): Map<String, FileInfoTree> {
        return DesktopJsonUtils.JSON.decodeFromString(fileInfoTree)
    }

    override fun getClipFiles(): List<ClipFile> {
        val fileInfoTreeMap = getFileInfoTreeMap()
        return getFilePaths().flatMap { path ->
            val fileInfoTree = fileInfoTreeMap[path.fileName.toString()]!!
            fileInfoTree.getClipFileList(path)
        }
    }

    override fun getIdentifierList(): List<String> {
        return identifiers
    }

    override fun getClipType(): Int {
        return ClipType.FILE
    }

    override fun getSearchContent(): String {
        return relativePathList.joinToString(separator = " ") { path ->
            Paths.get(path).fileName.toString().lowercase()
        }
    }

    override fun update(
        data: Any,
        md5: String,
    ) {}

    override fun clear(
        realm: MutableRealm,
        clearResource: Boolean,
    ) {
        if (clearResource) {
            for (path in getFilePaths()) {
                DesktopOneFilePersist(path).delete()
            }
        }
        realm.delete(this)
    }

    override fun fillDataFlavor(map: MutableMap<DataFlavor, Any>) {
        val fileList: List<File> = getFilePaths().map { it.toFile() }
        map[DataFlavor.javaFileListFlavor] = fileList

        if (currentPlatform().isLinux()) {
            val content =
                fileList.joinToString(
                    separator = "\n",
                    prefix = "copy\n",
                ) { it.toURI().toString() }
            val inputStream = ByteArrayInputStream(content.toByteArray(StandardCharsets.UTF_8))
            map[GNOME_COPIED_FILES_FLAVOR] = inputStream
        }
    }
}

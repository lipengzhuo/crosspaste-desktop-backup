package com.crosspaste.dao.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.mongodb.kbson.ObjectId

@Serializable
@SerialName("label")
class ClipLabel : RealmObject {

    companion object {}

    @PrimaryKey
    @Transient
    var id: ObjectId = ObjectId()
    var color: Int = 0

    @Index
    var text: String = ""

    @Transient
    var createTime: RealmInstant = RealmInstant.now()

    constructor()

    constructor(color: Color, text: String) {
        this.color = color.toArgb()
        this.text = text
    }

    fun getColor(): Color {
        return Color(color)
    }
}
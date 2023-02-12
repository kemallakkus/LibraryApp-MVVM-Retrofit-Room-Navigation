package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
data class Book(
    val authors: List<Author>,
    val bookshelves: List<String>,
    val copyright: Boolean,
    val download_count: Int,
    val formats: Formats,
    val id: Int,
    val languages: List<String>,
    val media_type: String?,
    val subjects: List<String>,
    val title: String?,
    val translators: List<Translator>
):Parcelable{
    override fun hashCode(): Int {
        var result = id.hashCode()
        if (formats.imagejpeg.isNullOrEmpty()){
            result = 31*result+formats.imagejpeg.hashCode()
        }
        return result
    }
}
package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookInfonmation")
@Parcelize
data class BookLocal(

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "imagejpeg")
    val imagejpeg: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    var selected: Boolean = false

) : Parcelable

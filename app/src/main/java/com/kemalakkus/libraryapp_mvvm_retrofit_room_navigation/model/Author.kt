package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val birth_year: Int,
    val death_year: Int,
    val name: String
):Parcelable
package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model



data class BookSet(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: ArrayList<Book>
)
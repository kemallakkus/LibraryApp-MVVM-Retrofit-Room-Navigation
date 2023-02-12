package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.util

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider: SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object{
        const val AUTHORITY = "com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.util"
        const val MODE : Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }

}
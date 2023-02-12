package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.repository

import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.api.ApiService
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookSet
import retrofit2.Response
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val apiService: ApiService){

    suspend fun getAllBooks() = apiService.getAllBooks()

    suspend fun getBooksByCategory(category:String): Response<BookSet> {
        return apiService.getBooksByCategory(category)
    }

    suspend fun getSearchBook(searchQuery: String): Response<BookSet>{
        return apiService.getSearchBook(searchQuery)
    }

}


    /*@Inject
    constructor(private val apiService: ApiService) {
        //suspend fun getAllBooks() = apiService.getAllBooks()
        suspend fun getAllBooks(): Response<BookSet> {
            return apiService.getAllBooks(page = "page")
        }
}*/

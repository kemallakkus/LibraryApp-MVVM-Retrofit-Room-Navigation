package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.api

import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.*
import dagger.Provides
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("books")
    suspend fun getAllBooks(): Response<BookSet>

    @GET("books")
    suspend fun getBooksByCategory(@Query ("topic") category: String): Response<BookSet>

    @GET("books")
    suspend fun getSearchBook(@Query("search") searchQuery: String): Response<BookSet>
}
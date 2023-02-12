package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.repository

import androidx.annotation.WorkerThread
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.db.BookDao
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.Book
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRepositoryDao @Inject constructor(private val bookDao: BookDao) {

    val bookList :  Flow<List<BookLocal>> = bookDao.getAllBook()

    @WorkerThread
    suspend fun upsert(book:BookLocal) = withContext(Dispatchers.IO){
        bookDao.upsert(book)
    }

    @WorkerThread
    suspend fun delete(book: BookLocal) = withContext(Dispatchers.IO){
        bookDao.delete(book)
    }

}
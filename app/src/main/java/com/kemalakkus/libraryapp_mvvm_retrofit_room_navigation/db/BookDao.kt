package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.Book
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(book: BookLocal)

    @Delete
    suspend fun delete(book: BookLocal)

    @Query("SELECT * FROM bookInfonmation")
    fun getAllBook() : Flow<List<BookLocal>>

}
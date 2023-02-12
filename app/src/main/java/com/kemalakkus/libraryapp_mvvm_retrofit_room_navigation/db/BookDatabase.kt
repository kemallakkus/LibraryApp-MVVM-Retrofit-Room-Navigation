package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.Book
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookLocal
import kotlinx.coroutines.internal.synchronized


@Database(entities = [BookLocal::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {

    abstract fun bookDao() : BookDao

    /*companion object{

        @Volatile
        private var instance : BookDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: kotlin.synchronized(lock){

            instance ?:makeDatabase(context).also{
                instance = it
            }

        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,BookDatabase::class.java,"book.db"
        ).fallbackToDestructiveMigration().build()

    }*/

}
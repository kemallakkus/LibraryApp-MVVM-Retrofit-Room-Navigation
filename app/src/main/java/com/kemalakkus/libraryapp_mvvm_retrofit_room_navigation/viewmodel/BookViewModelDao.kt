package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.Book
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookLocal
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.repository.BookRepositoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModelDao @Inject constructor(private val bookRepositoryDao: BookRepositoryDao): ViewModel(){

    val getAllBook : LiveData<List<BookLocal>>
    get()=
        bookRepositoryDao.bookList.flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)

    fun upsert(user:BookLocal){
        viewModelScope.launch {
            bookRepositoryDao.upsert(user)
        }

    }

    fun delete(user:BookLocal){
        viewModelScope.launch {
            bookRepositoryDao.delete(user)
        }
    }

}
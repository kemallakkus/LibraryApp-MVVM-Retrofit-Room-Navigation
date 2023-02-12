package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookSet
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.repository.BookRepository
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel
@Inject
constructor(private val repository: BookRepository): ViewModel() {

    /*private val _response = MutableLiveData<List<BookSet>>()
    val responseBook : LiveData<List<BookSet>>
    get() = _response*/
    var allBooks = MutableLiveData<BookSet>()
    //val bookLoading = MutableLiveData<Boolean>()
    //val bookError = MutableLiveData<Boolean>()
    val bookLoading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()




    fun getAllBooks() = viewModelScope.launch {
        bookLoading.value = true

        val response= repository.getAllBooks()
        if (response.isSuccessful){
            response.body()?.let {
                allBooks.value = it
            }
        }else{
            //bookLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }


}
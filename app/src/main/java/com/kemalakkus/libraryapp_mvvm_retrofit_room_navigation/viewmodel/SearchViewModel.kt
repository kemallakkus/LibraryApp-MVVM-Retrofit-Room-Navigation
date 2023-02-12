package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookSet
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject
constructor(private val repository: BookRepository): ViewModel(){

    val searchBook = MutableLiveData<BookSet>()
    val searchLoading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()


    fun getSearchBook(search: String) = viewModelScope.launch {
        val response =  repository.getSearchBook(search)
        if (response.isSuccessful){
            response.body()?.let {
                searchBook.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}
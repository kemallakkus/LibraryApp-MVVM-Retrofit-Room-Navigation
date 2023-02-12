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
class CategoryViewModel@Inject
constructor(private val repository: BookRepository): ViewModel() {

    var allBooks = MutableLiveData<BookSet>()
    private val disposable= CompositeDisposable()

    fun getBooksByCategory(topic:String) = viewModelScope.launch {
        val response = repository.getBooksByCategory(topic)
        if (response.isSuccessful){
            response.body()?.let {
                allBooks.value = it
            }
        }


    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}
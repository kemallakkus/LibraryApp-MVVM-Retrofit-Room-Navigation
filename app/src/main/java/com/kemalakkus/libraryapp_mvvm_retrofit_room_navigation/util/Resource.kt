package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.util

sealed class Resource<T>(

    val data: T? = null, //data herhangi veri tipinde ve null olabilir
    val message : String? = null // hata olunca gelen mesaj null olabilir

){

    class Success<T>(data : T): Resource<T>(data)
    class Error<T>(message: String): Resource<T>(message = message)
    class Loading<T>: Resource<T>() // yükleme durumu hiç bişey geri göndermeyecek
    class Unspecified<T> : Resource<T>()
}

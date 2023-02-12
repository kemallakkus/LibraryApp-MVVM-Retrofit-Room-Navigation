package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.di

import android.content.Context
import androidx.room.Room
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.api.ApiService
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.db.BookDao
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.db.BookDatabase
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.repository.BookRepositoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBookDao(bookDatabase: BookDatabase):BookDao = bookDatabase.bookDao()

    @Provides
    @Singleton
    fun providesBookDatabase(@ApplicationContext context : Context):BookDatabase
        = Room.databaseBuilder(context,BookDatabase::class.java,"BookDatabase").allowMainThreadQueries().build()

    @Provides
    fun providesBookRepositoryDao(bookDao: BookDao): BookRepositoryDao
        =BookRepositoryDao(bookDao)

    val BASE_URL = "https://gutendex.com/"
    //val NETWORK_TIMEOUT = 60L
    //val API_KEY = ""
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providerApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /*

    @Provides
    @Singleton
    fun ProvidesBaseURL() = BASE_URL

    @Provides
    @Singleton
    fun ConnectionTimeout() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun ProvidesGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun ProvideOKHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val requestInterceptor = Interceptor{chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        OkHttpClient
            .Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    fun provideRetrofit(baseUrl : String, gson: Gson, client: OkHttpClient): ApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)

     */



}
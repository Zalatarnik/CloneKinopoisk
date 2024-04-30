package com.example.clonekinopoisk.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideApi2(): Api {
        return Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech/api/")
            .client(
                OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}
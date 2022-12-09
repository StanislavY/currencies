package com.example.order.app.domain.di

import com.example.order.datasource.Server.API
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*@Module
@InstallIn(SingletonComponent::class)*/
object AppModule {
   /* private val baseUrl="https://currate.ru/api/"
    @Provides
    fun getRetrofit(): API {
        val retrofit1C = Retrofit.Builder()
            .baseUrl((baseUrl))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))//.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createOkHttpClient())
            .build()
        return retrofit1C.create(API::class.java)

    }
    @Singleton
    @Provides
    private fun createOkHttpClient(): OkHttpClient {
        val httpClient= OkHttpClient.Builder()
        return httpClient.build()

    }*/
}
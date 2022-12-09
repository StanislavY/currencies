package com.example.order.datasource.Server


import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws


class Retrofit1C @Inject constructor() {
    private val baseUrl="https://currate.ru/api/"
    fun getRetrofit(): API {
        val retrofit1C =Retrofit.Builder()
            .baseUrl((baseUrl))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))//.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createOkHttpClient())
            .build()
        return retrofit1C.create(API::class.java)

    }
    private fun createOkHttpClient():OkHttpClient{
        val httpClient=OkHttpClient.Builder()
           return httpClient.build()

    }


}
package com.example.order.datasource.server


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RetrofitService @Inject constructor() {
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
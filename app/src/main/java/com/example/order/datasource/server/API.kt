package com.example.order.datasource.server

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {




    @GET("?get=currency_list&key=39019b3af300a1027141bb1d9eb2354e")
    fun getDataFrom1C(

    ): Call<ServerResponseData>

    @GET("?get=rates")
    fun getPairs(@Query("pairs") pairsList:String,@Query("key") apiKey:String
    ):Call<ServerResponseDataPairs>


}
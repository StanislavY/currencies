package com.example.order.datasource.Server

import com.example.order.app.domain.model.ListItem
import com.example.order.core.GlobalConstAndVars
import retrofit2.Call
import retrofit2.http.*

interface API {




    @GET("?get=currency_list&key=39019b3af300a1027141bb1d9eb2354e")
    fun getDataFrom1C(

    ): Call<ServerResponseData>

    @GET("?get=rates")
    fun getPairs(@Query("pairs") pairsList:String,@Query("key") apiKey:String
    ):Call<ServerResponseDataPairs>

    @POST("SET")
    fun pullDataTo1C(@Body result:List<ListItem>):Call<List<ServerResponseData>>





}
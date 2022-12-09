package com.example.order.datasource.Server

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class ServerResponseDataPairs @Inject constructor (



    @SerializedName("data") var id1: Any,
    /*@SerializedName("NumCode") var id2: String?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Value") val value: String?*/


)

package com.example.order.datasource.Server

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class ServerResponseData @Inject constructor(



    @SerializedName("data") var id1: MutableList<String>?,
    /*@SerializedName("NumCode") var id2: String?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Value") val value: String?*/


)

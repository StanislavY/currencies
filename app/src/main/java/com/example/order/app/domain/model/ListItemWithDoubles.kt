package com.example.order.app.domain.model

data class ListItemWithDoubles(
    var id1: String,
    var id2: String,
    var name: String,
    var value: Double,
    var secondCurFlag: String,
    var countryFirstCur: String,
    var countrySecondCur: String,
    var favorite: String
)
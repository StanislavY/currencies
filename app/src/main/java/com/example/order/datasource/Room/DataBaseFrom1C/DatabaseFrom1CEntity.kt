package com.example.order.datasource.Room.DataBaseFrom1C

import androidx.room.Entity


@Entity(primaryKeys = ["id1","id2"])
data class DatabaseFrom1CEntity(

    var id1: String,
    var id2: String,
    var name:String,
    var value:String,
    var secondCurFlag:String,
    var countryFirstCur:String,
    var countrySecondCur:String,
    var favorite:String


)
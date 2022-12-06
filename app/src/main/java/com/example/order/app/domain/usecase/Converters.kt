package com.example.order.app.domain.usecase

import androidx.lifecycle.ViewModel
import com.example.order.app.domain.model.SearchItem
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.datasource.Room.DataBaseFrom1C.DatabaseFrom1CEntity
import com.example.order.datasource.Room.DatabaseResult.ResultEntity
import com.example.order.datasource.Server.ServerResponseData
import java.util.*
import kotlin.collections.ArrayList

open class Converters : ViewModel() {

    fun converterFromStringToMutableListItem(stringToConvert: String): List<ListItem> {
        return stringToConvert.removeRange(0..28).replace(" ", "").replace("=", "").dropLast(2)
            .split(",").toList().map {
            ListItem(
                it.removeRange(GlobalConstAndVars.FIRST_INDEX_OF_SECOND_CURRENCY..it.lastIndex),
                it.removeRange(GlobalConstAndVars.FIRST_INDEX_OF_CROSSCOUSE..it.lastIndex)
                    .removeRange(0 until GlobalConstAndVars.FIRST_INDEX_OF_SECOND_CURRENCY),
                "",
                it.removeRange(0..5), "", "", "", "")
        }

    }

    fun converterFromResponseServerToMainList(serverResponse: ServerResponseData?): List<ListItem> {
        val convertedListItem: MutableList<ListItem> = mutableListOf()


        serverResponse?.id1?.map { ListItem(it, "Список валют", "", "", "", "", "", "") }






        return convertedListItem

    }

    /* private fun convertMainListFromStrings (id1:ServerResponseData):ListItem{
        return ListItem(id1!!,id2!!,name!!, value!!)

    }*/
    fun convertMainListToEntityDB1C(
        id1: String,
        id2: String,
        name: String,
        value: String,
        secondCurFlag:String,
        countryFirstCur:String,
        countrySecondCur:String,
        favorite:String


    ): DatabaseFrom1CEntity {
        val databaseFrom1CEntity =
            DatabaseFrom1CEntity("", "", "", "", "", "", "", "")

        databaseFrom1CEntity.id1 = id1
        databaseFrom1CEntity.id2 = id2
        databaseFrom1CEntity.name = name
        databaseFrom1CEntity.value = value
        databaseFrom1CEntity.secondCurFlag=secondCurFlag
        databaseFrom1CEntity.countryFirstCur=countryFirstCur
        databaseFrom1CEntity.countrySecondCur=countrySecondCur
        databaseFrom1CEntity.favorite=favorite



        return databaseFrom1CEntity
    }

    fun convertEntityDB1CToMainList(entityList: List<DatabaseFrom1CEntity>): List<ListItem> {
        return entityList.map {
            ListItem(it.id1, it.id2, it.name, it.value, "", "", "", "")

        }


    }

    fun convertEntityResultToMainList(entityList: List<ResultEntity>): List<ListItem> {
        return entityList.map { ListItem(it.id1, it.id2, it.name, it.uid, "", "", "", it.value) }
    }

    fun convertRemListToResultEntity(remListItem: List<ListItem>): List<ResultEntity> {

        return remListItem.map {
            ResultEntity(it.id1, it.id2, it.name, it.favorite, ""

            )
        }

    }

    fun convertListItemToItemStorage(listItem: List<ListItem>): ArrayList<SearchItem> {
        val arrListOfItemStorage = ArrayList<SearchItem>()

        for (mainList in listItem) {
            arrListOfItemStorage.add(SearchItem(mainList.id1,
                mainList.id2,
                mainList.name,
                mainList.value,
                mainList.secondCurFlag,
                mainList.countryFirstCur,
                mainList.countrySecondCur,
                mainList.favorite))
        }
        return arrListOfItemStorage

    }

    fun convertItemStorageToMainList(arrayList:ArrayList<SearchItem>):List<ListItem>{
        val mainList= mutableListOf<ListItem>()
        for (item in arrayList) {
            mainList.add(ListItem(
                item.id1.toString(),
                item.id2.toString(),
                item.name.toString(),
                item.value.toString(),
                item.secondCurFlag.toString(),
                item.countryFirstCur.toString(),
                item.countrySecondCur.toString(),
                item.favorite.toString()))


        }
        return mainList

    }
    fun convertEntityResultToMainListForOrederList(entityList: List<ResultEntity>):List<ListItem>{
        return entityList.map {  ListItem(it.uid, it.id1, it.name, it.value,"","","","") }
    }




    private fun swapValuesForOrdersListCreating(
        id1: String,
        id2: String,
        name: String,
        value: String,
        uid: String
    ): ListItem {

        return ListItem("0", uid, name, value,"","","","")
    }





}













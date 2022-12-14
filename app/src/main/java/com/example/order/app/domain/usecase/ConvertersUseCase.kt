package com.example.order.app.domain.usecase

import androidx.lifecycle.ViewModel
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.model.ListItemWithDoubles
import com.example.order.app.domain.model.SearchItem
import com.example.order.core.GlobalConstAndVars
import com.example.order.datasource.room.DataBaseFrom1C.DatabaseFrom1CEntity
import com.example.order.datasource.room.databaseResult.ResultEntity
import javax.inject.Inject

open class ConvertersUseCase @Inject constructor() : ViewModel() {

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

    fun convertItemStorageToListItem(arrayList:ArrayList<SearchItem>):List<ListItem>{

        return  arrayList.map { ListItem(
            it.id1.toString(),
            it.id2.toString(),
            it.name.toString(),
            it.value.toString(),
            it.secondCurFlag.toString(),
            it.countryFirstCur.toString(),
            it.countrySecondCur.toString(),
            it.favorite.toString()) }

    }
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
     fun convertValueToDoubleInListItem(list:List<ListItem>):List<ListItemWithDoubles>{

        return list.map {
            ListItemWithDoubles(it.id1,
                it.id2,
                it.name,
                it.value.toDouble(),
                it.secondCurFlag,
                it.countryFirstCur,
                it.countrySecondCur,
                it.favorite)
        }
    }
    fun convertValueToStringInListItem(list:List<ListItemWithDoubles>):List<ListItem>{
        return list.map {
            ListItem(it.id1,
                it.id2,
                it.name,
                it.value.toString(),
                it.secondCurFlag,
                it.countryFirstCur,
                it.countrySecondCur,
                it.favorite)
        }
    }


}













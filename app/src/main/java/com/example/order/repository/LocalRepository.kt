package com.example.order.repository

import com.example.order.app.domain.model.ListItem
import com.example.order.datasource.Room.DatabaseResult.ResultEntity

interface LocalRepository {
    fun putDataFromServer1CToLocalDatabase(listItemFromServer:List<ListItem>)
    fun getAllDataDB1CEntity(): List<ListItem>
    fun deleteAllData()
    fun getAllUnfinishedDataDBResultEntityToMainList():List<ListItem>
    fun putDataDBFromListItem(resultListItem:List<ListItem>)
    fun putDataToResultDBFromListItem(resultListItem:List<ListItem>)
    fun getAllUnfinishedDataDBResultEntity():List<ResultEntity>
    fun insertToDBResultFromResultEntity(data: ResultEntity)
    fun getAllDatafromDBResult():List<ResultEntity>


}
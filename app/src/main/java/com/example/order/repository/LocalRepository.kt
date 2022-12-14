package com.example.order.repository

import com.example.order.app.domain.model.ListItem
import com.example.order.datasource.room.databaseResult.ResultEntity

interface LocalRepository {
    fun putDataFromServerToLocalDatabase(listItemFromServer:List<ListItem>)
    fun getAllDataDB1CEntity(): List<ListItem>
    fun deleteAllData()
    fun getAllUnfinishedDataDBResultEntityToMainList():List<ListItem>
    fun putDataDBFromListItem(resultListItem:List<ListItem>)
    fun writeFavoriteToDb(resultListItem:List<ListItem>)
    fun insertToDBResultFromResultEntity(data: ResultEntity)
    fun getAllDataFromDBResult():List<ResultEntity>


}
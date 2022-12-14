package com.example.order.repository

import com.example.order.app.domain.model.ListItem
import com.example.order.datasource.room.DataBaseFrom1C.DatabaseFrom1CDAO
import com.example.order.datasource.room.DataBaseFrom1C.DatabaseFrom1CEntity
import com.example.order.datasource.room.databaseResult.ResultEntity
import com.example.order.app.domain.usecase.ConvertersUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImpl @Inject constructor(private val localDataSource: DatabaseFrom1CDAO) : LocalRepository {

    private val converter: ConvertersUseCase = ConvertersUseCase()
    override fun putDataFromServerToLocalDatabase(listItemFromServer: List<ListItem>) {
       for (mainList in listItemFromServer) {
           val data:DatabaseFrom1CEntity=converter.convertMainListToEntityDB1C(mainList.id1,mainList.id2,mainList.name,mainList.value,mainList.secondCurFlag,mainList.countryFirstCur,mainList.countrySecondCur,mainList.favorite)
            insertToDB(data)

          }

    }

    private fun insertToDB(data:DatabaseFrom1CEntity){
        localDataSource.insert(data)


    }

    override fun getAllDataDB1CEntity():List<ListItem> {
        return converter.convertEntityDB1CToMainList(localDataSource.all1C())

    }

    override fun deleteAllData(){
        localDataSource.deleteall()
    }

    override fun getAllUnfinishedDataDBResultEntityToMainList(): List<ListItem> {
        return converter.convertEntityResultToMainList(localDataSource.getAllUnfinishedResult())
    }

    override fun putDataDBFromListItem(resultListItem: List<ListItem>) {

       resultListItem.forEach {

          insertToDB(
              converter.convertMainListToEntityDB1C(it.id1,it.id2,it.name,it.value,it.secondCurFlag,it.countryFirstCur,it.countrySecondCur,it.favorite))  }

    }

    override fun writeFavoriteToDb(resultListItem: List<ListItem>) {
        val data=converter.convertRemListToResultEntity(resultListItem)
        for (mainList in data) {

            insertToDBResultFromResultEntity(mainList)

        }
    }


    override fun insertToDBResultFromResultEntity(data: ResultEntity){
        localDataSource.insertDataToResult(data)


    }

    override fun getAllDataFromDBResult(): List<ResultEntity> {
      return  localDataSource.allFromResultDB()

    }


}


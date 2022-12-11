package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem
import com.example.order.core.App
import com.example.order.core.GlobalConstAndVars
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import javax.inject.Inject

class CreateListOfAllItemsDBUseCaseImpl @Inject constructor(
       ): CreateListOfAllItemsUseCase {
    private val converters:ConvertersUseCase=ConvertersUseCase()
    private val localRepository1C: LocalRepository = LocalRepositoryImpl(App.get1CDAO())
    private val getAndSetGlobalList:OperationsWithListsUseCase=OperationsWithListsUseCaseImpl()

    override suspend fun getCurrenciesList(): List<ListItem> {


        return getAndSetGlobalList.executeSetMainList(fillFlagsFavoritesAndCountriesInCurList(localRepository1C.getAllDataDB1CEntity()))
    }
    private fun fillFlagsFavoritesAndCountriesInCurList(listFromDB:List<ListItem>):List<ListItem>{
       listFromDB.forEach { gl->gl.apply {//заполняем флаги и наименования валют в списке валют
           with(GlobalConstAndVars.COUNTRIES_AND_CURRENCIES_lIST){
               name=firstOrNull { it.id1==gl.id1 }?.value ?: "0"
               secondCurFlag=firstOrNull { it.id1==gl.id2 }?.value ?: "0"
               countryFirstCur=firstOrNull { it.id1==gl.id1 }?.favorite ?: "0"
               countrySecondCur=firstOrNull { it.id1==gl.id2 }?.favorite ?: "0"

           }

           }
           }
        listFromDB.forEach { gl->gl.apply {//заполняем отметки избранного в в списке валют
            with(converters.convertEntityResultToMainList(localRepository1C.getAllDataFromDBResult())){
              favorite=firstOrNull { it.id1==gl.id1&&it.id2==gl.id2 }?.favorite ?: "0"
            }
        }
        }
        return listFromDB
    }


}











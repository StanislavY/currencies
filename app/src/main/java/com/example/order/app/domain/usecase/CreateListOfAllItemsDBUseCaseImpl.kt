package com.example.order.app.domain.usecase

import com.example.order.core.GlobalConstAndVars

import com.example.order.app.domain.model.ListItem
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.example.order.core.App
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

class CreateListOfAllItemsDBUseCaseImpl @Inject constructor(): CreateListOfAllItemsUseCase {
    private val localRepository1C: LocalRepository = LocalRepositoryImpl(App.get1CDAO())
    private val converters:Converters= Converters()
    override suspend fun getCurrenciesList(): List<ListItem> {
        var startList: List<ListItem> = listOf()
        val dataFrom1C: List<ListItem>
        if (GlobalConstAndVars.LIST_KEY != "0") {
            dataFrom1C = GlobalConstAndVars.listItemFromDb

        }
        else {
            if (GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST == 0) {
                dataFrom1C = localRepository1C.getAllDataDB1CEntity()
                GlobalConstAndVars.listItemFromDb=dataFrom1C

                startList=fillFlagsFavoritesAndCountriesInCurList(dataFrom1C)



                GlobalConstAndVars.GLOBAL_LIST=startList


            }

        }





        return startList
    }
    private fun fillFlagsFavoritesAndCountriesInCurList(listFromDB:List<ListItem>):List<ListItem>{
       listFromDB.forEach { gl->gl.apply {//заполняем флаги и наименования валют в списке валют
           with(GlobalConstAndVars.COUNTRIES_AND_CURRENCIES_lIST){
               name=firstOrNull { it.id1==gl.id1 }?.value ?: "0"
               secondCurFlag=firstOrNull { it.id1==gl.id2 }?.value ?: "0"
               countryFirstCur=firstOrNull { it.id1==gl.id1 }?.favorite ?: "0"
               countrySecondCur=firstOrNull { it.id1==gl.id2 }?.favorite ?: "0"
               /*favorite=firstOrNull { it.id1==gl.favorite }?.id2 ?: "0"*/


           }

           }
           }
        listFromDB.forEach { gl->gl.apply {//заполняем отметки избранного в в списке валют
            with(converters.convertEntityResultToMainList(localRepository1C.getAllDatafromDBResult())){
              favorite=firstOrNull { it.id1==gl.id1&&it.id2==gl.id2 }?.favorite ?: "0"
            }
        }
        }
        return listFromDB
    }



    private fun createOrdersList(listItem: List<ListItem>): List<ListItem> {
        val startListItem: List<ListItem> = listItem.distinctBy { it.id1
        }
        val convertListItem:MutableList<ListItem> = mutableListOf()
        for (startList1 in startListItem) {
            convertListItem.add(swapValuesForOrdersListCreating(startList1.id1,startList1.id2,startList1.name,startList1.value))

        }
        return convertListItem
    }
      private fun makeListFromDB(key: String, listItem:List<ListItem>): List<ListItem> {
          val tempListItem: MutableList<ListItem> = mutableListOf()
          for (mainList in listItem) {
              if (mainList.id1 == key) {
                  tempListItem.add(mainList)
              }
          }
          for (mainList in tempListItem) {
               mainList.value = mainList.name

                  }

          return tempListItem.distinctBy { it.name to it.id1 to it.id2 }
      }

    private fun makeStartList(listItem: List<ListItem>): List<ListItem> {
        val startListItem: List<ListItem> = listItem.distinctBy { it.id1 }
        val convertListItem:MutableList<ListItem> = mutableListOf()
         for (startList1 in startListItem) {
           convertListItem.add(swapValuesForStartListCreating(startList1.id1,startList1.id2,startList1.name,startList1.value))

        }
        return convertListItem
    }

    private fun swapValuesForStartListCreating (id1:String, id2:String, name: String, value:String):ListItem{
        val objectForChange = ListItem(id1,id2,name,value,"","","","Ариари")

      /*  objectForChange.name=objectForChange.id1
        objectForChange.id2=objectForChange.id1
        objectForChange.id1="0"*/
        return objectForChange
    }

    private fun swapValuesForOrdersListCreating(
        id1: String,
        id2: String,
        name: String,
        value: String,

    ): ListItem {

        return ListItem("0", id1, name, value,"","","","Ариари")
    }
    private fun makeListOfWork(numberOfValues:Int, step:Double, nameOfField:String):MutableList<ListItem>{
        val workListItem: MutableList<ListItem> = mutableListOf()
        var valueForWork=0.000
        for (i in 1..numberOfValues){
            valueForWork += step
            val roundedNumber = DecimalFormat("#.###")
            roundedNumber.roundingMode = RoundingMode.DOWN
                workListItem.add(
                    ListItem(
                        nameOfField,
                        roundedNumber.format(valueForWork).toString(),
                        roundedNumber.format(valueForWork).toString(),
                        GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST
                        ,"","","","Ариари")
                )
            }

        return workListItem

    }

}











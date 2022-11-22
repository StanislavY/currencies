package com.example.order.app.domain.usecase

import com.example.order.core.GlobalConstAndVars

import com.example.order.app.domain.model.ListItem
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.example.order.core.App
import com.example.order.datasource.Room.DatabaseResult.ResultEntity
import java.math.RoundingMode
import java.text.DecimalFormat

class CreateListOfAllItemsFrom1CDBCaseImpl: CreateListOfAllItemsFrom1CDBCase {



    private val localRepository1C: LocalRepository = LocalRepositoryImpl(App.get1CDAO())
    private val hoursWorked=makeListOfWork(
       GlobalConstAndVars.NUMBERS_OF_VALUES_FOR_WORKED_HOURS,
       GlobalConstAndVars.STEP_FOR_WORKED_HOURS,"Отработано часов")
    private val converters:Converters= Converters()

    // саделать маски для имен в главном списке
    override fun getListForChoice(): List<ListItem> {
        var startList: List<ListItem> = listOf()
        val dataFrom1C: List<ListItem>
        val dataFrom1CResuldEntity:List<ResultEntity>

        //TODO() хардкод ниже - убрать
/*

        val quality:MutableList<ListItem> = mutableListOf(
            ListItem("ДоплатаЗаКачество","Доплата за качество 0%","0", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("ДоплатаЗаКачество","Доплата за качество 20%","20", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST)
        )
        val difficult:MutableList<ListItem> = mutableListOf(
            ListItem("ДоплатаЗаТяжесть(Сложность)","ДоплатаЗаКачество0%","0", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("ДоплатаЗаТяжесть(Сложность)","ДоплатаЗаКачество12%","12", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST)
        )
        val refill:MutableList<ListItem> = mutableListOf(
            ListItem("ДоплатаЗаЗаправку","ДоплатаЗаЗаправку0%","0", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("ДоплатаЗаЗаправку","ДоплатаЗаЗаправку20%","20", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST)
        )
        val weekends:MutableList<ListItem> = mutableListOf(
            ListItem("ДоплатаЗаВыходные","ДоплатаЗаВыходные0%","0", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("ДоплатаЗаВыходные","ДоплатаЗаВыходные100%","100", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST)
        )
        val steepSlope:MutableList<ListItem> = mutableListOf(
            ListItem("КрутойСклон","КрутойСклон0%","0", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("КрутойСклон","КрутойСклон20%","20", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST)
        )
        val shortRun:MutableList<ListItem> = mutableListOf(
            ListItem("КороткийГон","КороткийГон0%","0", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("КороткийГон","КороткийГон20%","20", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST)
        )
        val student:MutableList<ListItem> = mutableListOf(
            ListItem("Ученические","Ученические0%","0", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("Ученические","Ученические20%","20", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST),
            ListItem("Ученические","Ученические30%","30", GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST)

        )
*/


        if (GlobalConstAndVars.LIST_KEY != "0") {
            dataFrom1C = GlobalConstAndVars.listItemFromDb

        }
        else {
            if (GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST == 0) {
                dataFrom1C = localRepository1C.getAllDataDB1CEntity()
                GlobalConstAndVars.listItemFromDb=dataFrom1C
                startList=makeStartList(dataFrom1C/*+hoursWorked+
                        quality+difficult+refill+weekends+steepSlope+shortRun+student*/)+dataFrom1C/*+hoursWorked+ quality+difficult+refill+weekends+steepSlope+shortRun+student*/
                GlobalConstAndVars.GLOBAL_LIST=startList

            }
            else{
               dataFrom1C=converters.convertEntityResultToMainListForOrederList(localRepository1C.getAllDatafromDBResult())
                GlobalConstAndVars.listItemFromDb=dataFrom1C
                startList=createOrdersList(dataFrom1C)+dataFrom1C
                GlobalConstAndVars.GLOBAL_LIST=startList



            }






        }
      /*  if (GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST == 0) {
            startList=makeStartList(dataFrom1C+hoursWorked+
                    quality+difficult+refill+weekends+steepSlope+shortRun+student)+dataFrom1C+hoursWorked+ quality+difficult+refill+weekends+steepSlope+shortRun+student
            GlobalConstAndVars.GLOBAL_LIST=startList

        }
        else{
            startList=createOrdersList()


        }*/




        return startList
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
        val objectForChange = ListItem(id1,id2,name,value)

        objectForChange.name=objectForChange.id1
        objectForChange.id2=objectForChange.id1
        objectForChange.id1="0"
        return objectForChange
    }

    private fun swapValuesForOrdersListCreating(
        id1: String,
        id2: String,
        name: String,
        value: String,

    ): ListItem {

        return ListItem("0", id1, name, value)
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
                    )
                )
            }

        return workListItem

    }

}











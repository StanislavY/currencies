package com.example.order.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.example.order.datasource.Room.DatabaseResult.ResultEntity
import com.example.order.core.App

class GetSelectionResultCaseImpl: GetSelectionResultCase {
    private val localDataSource: LocalRepository = LocalRepositoryImpl(App.get1CDAO())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rememberListOfChosenItems(listItem: ListItem): MutableList<ListItem> {
        val rememberedListItem:MutableList<ListItem> = GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
        val iterator=rememberedListItem.iterator()
        while (iterator.hasNext()) {
            val item=iterator.next()
            if (item.id1 == listItem.id1) {
                iterator.remove()
            }
        }
        rememberedListItem.add(listItem)
        GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS=rememberedListItem
        return GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
    }
    override fun makeOrderFinished() {
      /*  val listOfUnfinishedOrders = localDataSource.getAllUnfinishedDataDBResultEntity()
        for (orderFromDB in listOfUnfinishedOrders) {
            for (orderFromServer in GlobalConstAndVars.LIST_OF_FINISHED_ORDERS) {
                if (orderFromDB.uid == orderFromServer.id2) {
                    val tempResult= ResultEntity(orderFromDB.id1,orderFromDB.id2,orderFromDB.name,
                        GlobalConstAndVars.MARKER_OF_FINISHED_ORDER,orderFromDB.uid)
                    localDataSource.insertToDBResultFromResultEntity(tempResult)

                }



            }

        }
*/

    }

    override fun putListOfChosenItemToDB(data:MutableList<ListItem>) {
        localDataSource.putDataToResultDBFromListItem(data)

    }

    override fun getAllDataDBResultEntityToListItem():List<ListItem> {
        return localDataSource.getAllUnfinishedDataDBResultEntityToMainList()
    }


}



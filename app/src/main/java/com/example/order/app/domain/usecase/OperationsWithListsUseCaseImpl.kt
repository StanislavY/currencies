package com.example.order.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem

class OperationsWithListsUseCaseImpl: OperationsWithListsUseCase {
    @RequiresApi(Build.VERSION_CODES.N)
    override  fun executeGetMainList(): List<ListItem> {
        return  GlobalConstAndVars.GLOBAL_LIST


                }

    override  fun executeSetMainList(list:List<ListItem>): List<ListItem> {
        GlobalConstAndVars.GLOBAL_LIST=list
        return GlobalConstAndVars.GLOBAL_LIST

    }

    override  fun executeGetChosenItems(): MutableList<ListItem> {
        return  GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
    }

    override fun executeSetChosenItems(list: MutableList<ListItem>): MutableList<ListItem> {
        GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS=list
        return GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
    }


}









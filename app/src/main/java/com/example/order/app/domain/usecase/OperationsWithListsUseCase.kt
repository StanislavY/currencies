package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem


interface OperationsWithListsUseCase {
   fun executeGetMainList():List<ListItem>
    fun executeSetMainList(list:List<ListItem>):List<ListItem>
   fun executeGetChosenItems():MutableList<ListItem>
   fun executeSetChosenItems(list: MutableList<ListItem>): MutableList<ListItem>






}
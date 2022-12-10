package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem


interface OperationsWithListsUseCase {
   suspend fun executeGetMainList():List<ListItem>
   suspend fun executeSetMainList(list:List<ListItem>):List<ListItem>
   suspend fun executeGetChosenItems():MutableList<ListItem>
   suspend fun executeSetChosenItems(list: MutableList<ListItem>): MutableList<ListItem>






}
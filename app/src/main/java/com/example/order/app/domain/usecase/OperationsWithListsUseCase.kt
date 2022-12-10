package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem


interface OperationsWithListsUseCase {
   suspend fun getMainList():List<ListItem>



}
package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

interface CreateListOfAllItemsUseCase {
   suspend fun getCurrenciesList():List<ListItem>




}
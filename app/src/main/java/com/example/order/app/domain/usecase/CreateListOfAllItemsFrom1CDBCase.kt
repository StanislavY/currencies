package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

interface CreateListOfAllItemsFrom1CDBCase {
   suspend fun getListForChoice():List<ListItem>




}
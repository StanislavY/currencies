package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

interface LoadDataFrom1CCase {
   fun  executeDeletingDataFromDb()
   fun executeDownloadingDataFromServerToDB(listItemFromServer:List<ListItem>)
}
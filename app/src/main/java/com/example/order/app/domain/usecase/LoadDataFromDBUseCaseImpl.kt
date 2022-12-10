package com.example.order.app.domain.usecase

import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.example.order.app.domain.model.ListItem
import com.example.order.core.App
import javax.inject.Inject

class LoadDataFromDBUseCaseImpl @Inject constructor():LoadDataFrom1CCase {
    private val localRepository:LocalRepository=LocalRepositoryImpl(App.get1CDAO())
    override fun executeDeletingDataFromDb() {
        localRepository.deleteAllData()
    }

    override fun executeDownloadingDataFromServerToDB(listItemFromServer:List<ListItem>) {
        localRepository.putDataFromServer1CToLocalDatabase(listItemFromServer)
    }
}
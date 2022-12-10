package com.example.order.ui.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.model.SearchItem
import com.example.order.app.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor (





) : ViewModel() {
    private val listOfChosenItems:OperationsWithListsUseCase=OperationsWithListsUseCaseImpl()
    private val createLists: OperationsWithListsUseCase = OperationsWithListsUseCaseImpl()
    private val makeResultCase: FavoriteLogicUseCase = FavoriteLogicUseCasempl()
    private val liveDatatoObserve: MutableLiveData<AppState> = MutableLiveData()

    private val converters: ConvertersUseCase = ConvertersUseCase()

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Default+ SupervisorJob()+ CoroutineExceptionHandler { _, _ ->  })


    fun processAppState(): LiveData<AppState> {
        liveDatatoObserve.value = AppState.Loading(null)
        return liveDatatoObserve
    }


    fun processTheSelectedItem() = sendDataToList()

    private fun sendDataToList() {
        viewModelCoroutineScope.launch {   liveDatatoObserve.postValue(
            AppState.Success(createLists.executeGetMainList(
           ))) }
    }
   fun sendDataToList(list:List<ListItem>) {
        viewModelCoroutineScope.launch {   liveDatatoObserve.postValue(
            AppState.Success(list)) }
    }

    fun convertMainListToArrayListItem(listItem: List<ListItem>): ArrayList<SearchItem> {
        return converters.convertListItemToItemStorage(listItem)

    }
    fun convertArrayListItemToListItem(itemList: ArrayList<SearchItem>): List<ListItem> {
        return converters.convertItemStorageToListItem(itemList)

    }
    fun handleFavoriteButtonClick(listItem:ListItem){
        makeResultCase.executeAddingToFavorites(listItem)

    }

    fun makeItemFavoriteInDB(data:MutableList<ListItem>) {
        makeResultCase.executeMakingItemFavorite(data)

    }
    fun getListOfChosenItems():MutableList<ListItem>{
        var list: MutableList<ListItem> = mutableListOf()
        viewModelCoroutineScope.launch {
          list=  listOfChosenItems.executeGetChosenItems() }
        return list

    }


}




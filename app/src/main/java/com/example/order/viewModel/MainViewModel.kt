package com.example.order.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.model.SearchItem
import com.example.order.app.domain.usecase.*
import com.example.order.core.GlobalConstAndVars
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor (





) : ViewModel() {
    private val createLists: CreateListsForFirstAndSecondScreensCase = CreateListsForFirstAndSecondScreensCaseImpl()
    private val makeResultCase: GetSelectionResultCase = GetSelectionResultCaseImpl()
    private val liveDatatoObserve: MutableLiveData<AppState> = MutableLiveData()
    private val liveDataFavorite:MutableLiveData<AppState> = MutableLiveData()
    private val liveDataLastSort:MutableLiveData<String> = MutableLiveData()
    private val converters: Converters = Converters()

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Default+ SupervisorJob()+ CoroutineExceptionHandler { _, _ ->  })


    fun processAppState(): LiveData<AppState> {
        liveDatatoObserve.value = AppState.Loading(null)
        return liveDatatoObserve
    }

    fun processTheSelectedItem() = sendDataToList()

    private fun sendDataToList() {
        viewModelCoroutineScope.launch {   liveDatatoObserve.postValue(
            AppState.Success(createLists.getMainList(
            GlobalConstAndVars.LIST_KEY))) }
    }
   fun sendDataToList(list:List<ListItem>) {
        viewModelCoroutineScope.launch {   liveDatatoObserve.postValue(
            AppState.Success(list)) }
    }

    fun convertMainListToArrayListItem(listItem: List<ListItem>): ArrayList<SearchItem> {
        return converters.convertListItemToItemStorage(listItem)

    }
    fun convertArrayListItemToListItem(itemList: ArrayList<SearchItem>): List<ListItem> {
        return converters.convertItemStorageToMainList(itemList)

    }
    fun handleFavoriteButtonClick(listItem:ListItem){
        makeResultCase.executeAddingToFavorites(listItem)

    }

    fun makeItemFavoriteInDB(data:MutableList<ListItem>) {
        makeResultCase.putListOfChosenItemToDB(data)

    }


}




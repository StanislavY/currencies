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
    private val liveDataPopular: MutableLiveData<AppState> = MutableLiveData()
    private val liveDataFavorite:MutableLiveData<AppState> = MutableLiveData()
    private val liveDataLastSort:MutableLiveData<String> = MutableLiveData()
    private val converters: Converters = Converters()

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Default+ SupervisorJob()+ CoroutineExceptionHandler { _, _ ->  })


    fun processAppState(): LiveData<AppState> {
        liveDataPopular.value = AppState.Loading(null)
        return liveDataPopular
    }

    fun processTheSelectedItem() = requestData()

    private fun requestData() {
        viewModelCoroutineScope.launch {   liveDataPopular.postValue(
            AppState.Success(createLists.getMainList(
            GlobalConstAndVars.LIST_KEY))) }
    }
   fun postPopularList(list:List<ListItem>) {
        viewModelCoroutineScope.launch {   liveDataPopular.postValue(
            AppState.Success(list)) }
    }
    fun postFavoriteList(list:List<ListItem>) {
        viewModelCoroutineScope.launch {   liveDataFavorite.postValue(
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




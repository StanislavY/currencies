package com.example.order.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.order.BuildConfig
import com.example.order.app.domain.model.SearchItem
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.datasource.Server.Retrofit1C
import com.example.order.datasource.Server.ServerResponseData
import com.example.order.app.domain.usecase.AppState
import com.example.order.app.domain.usecase.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MainViewModel(
    private val createLists: CreateListsForFirstAndSecondScreensCase = CreateListsForFirstAndSecondScreensCaseImpl(),
    private val makeResultCase: GetSelectionResultCase = GetSelectionResultCaseImpl()




) : ViewModel() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val retrofit1C: Retrofit1C = Retrofit1C()
    private val converters: Converters = Converters()
    private val createListOfOrdersAndStartListItem: CreateListOfAllItemsFrom1CDBCase = CreateListOfAllItemsFrom1CDBCaseImpl()




    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Default+ SupervisorJob()+ CoroutineExceptionHandler { _, _ -> handleError() })

    private fun handleError() {}

    fun processAppState(): LiveData<AppState> {
        liveDataToObserve.value = AppState.Loading(null)
        return liveDataToObserve
    }

    fun processTheSelectedItem() = requestData()
    fun checkCompleteness(referenceListItem:List<ListItem>, listItemForCheck:List<ListItem>, dateOfOrder:String, worked:String):String{
        val differences:MutableList<ListItem> = mutableListOf()
        for (refValue in referenceListItem) {
            var count=0
            for (checkedValue in listItemForCheck) {
                if (refValue.id2==checkedValue.id1) {
                    count += 1
                }
            }
            if (count<1){
                differences.add(refValue)
            }

        }
        return if (differences.isNotEmpty()||listItemForCheck.isEmpty()||dateOfOrder==""||worked==""){
            "Данные наряда заполнены не полностью"
        } else "Данные в наряде заполнены корректно"
    }

    private fun requestData() {
        viewModelCoroutineScope.launch {   liveDataToObserve.postValue(
            AppState.Success(createLists.getMainList(
            GlobalConstAndVars.LIST_KEY))) }
    }
    fun convertMainListToArrayListItem(listItem: List<ListItem>): ArrayList<SearchItem> {
        return converters.convertListItemToItemStorage(listItem)

    }
    fun convertArrayListItemToMainList(itemList: ArrayList<SearchItem>): List<ListItem> {
        return converters.convertItemStorageToMainList(itemList)

    }
    fun handleFavoriteButtonClick(listItem:ListItem){
        makeResultCase.rememberListOfChosenItems(listItem)


    }

    fun makeOrdersFinishedVM() {
        makeResultCase.makeOrderFinished()

    }
    fun rememberListOfChosenItemsVM(itemItem:ListItem){
        makeResultCase.rememberListOfChosenItems(itemItem)
    }
    fun putDataToResultDB(data:MutableList<ListItem>) {
        makeResultCase.putListOfChosenItemToDB(data)

    }

    fun getAllDataDBResultEntityToListItem():List<ListItem>{
        return makeResultCase.getAllDataDBResultEntityToListItem()

    }

    fun pullDataToServer(resultListItem:List<ListItem>)  {
        liveDataToObserve.value = AppState.Loading(null)
        val apiKey: String = BuildConfig.APIKEY_FROM_1C
        if (apiKey.isBlank()) {
            AppState.Error(Throwable("You need API key"))
        } else {
            retrofit1C.getRetrofit().pullDataTo1C(resultListItem).enqueue(object :
                Callback<List<ServerResponseData>> {
                override fun onResponse(
                    call: Call<List<ServerResponseData>>,
                    response: Response<List<ServerResponseData>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value= AppState.Success(resultListItem)
                        GlobalConstAndVars.LIST_OF_FINISHED_ORDERS = response.body()!!
                        makeOrdersFinishedVM()

                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value =
                                AppState.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataToObserve.value =
                                AppState.Error(Throwable(message))
                        }
                    }
                }
                override fun onFailure(call: Call<List<ServerResponseData>>, t: Throwable) {
                    liveDataToObserve.value = AppState.Error(t)
                }
            })
        }

    }
   suspend fun getOrdersListFromDBResult(){
        createListOfOrdersAndStartListItem.getListForChoice()
    }
  suspend  fun getGlobalLIst(){
       createListOfOrdersAndStartListItem.getListForChoice()

    }








}




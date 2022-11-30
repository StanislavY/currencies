package com.example.order.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.order.R

import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.usecase.*
import com.example.order.core.GlobalConstAndVars
import com.example.order.datasource.Server.Retrofit1C
import com.example.order.datasource.Server.ServerResponseData
import com.example.order.datasource.Server.ServerResponseDataPairs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoadingViewModel(val liveDataToObserve:MutableLiveData<AppState> = MutableLiveData(),
                       private val retrofit1C: Retrofit1C = Retrofit1C(),
                       private val converters: Converters = Converters()
):ViewModel() {
    private val createGlobalListCase: CreateListOfAllItemsFrom1CDBCase = CreateListOfAllItemsFrom1CDBCaseImpl()
    private val loadFrom1CtoDBCase:LoadDataFrom1CCase=LoadDataFrom1CCaseImpl()

    fun getDataFromServerForDB(): LiveData<AppState> {

        return liveDataToObserve
    }
    fun clearDB(){
        loadFrom1CtoDBCase.executeDeletingDataFromDb()
    }
    fun putDataFromServer1CToLocalDatabase(listFromServer:List<ListItem>){
        loadFrom1CtoDBCase.executeDownloadingDataFrom1CToDB(listFromServer)

    }


    suspend fun getPairsList():String  { return suspendCoroutine {
        val x:List<ServerResponseData> = listOf()
        val pairsString=""

        val apiKey: String = "39019b3af300a1027141bb1d9eb2354e"
        if (apiKey.isBlank()) {
            AppState.Error(Throwable("You need API key"))
        } else {
            retrofit1C.getRetrofit().getDataFrom1C().enqueue(object :
                Callback<ServerResponseData> {
                override fun onResponse(
                    call: Call<ServerResponseData>,
                    response: Response<ServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        GlobalConstAndVars.PAIRS_lIST= response.body()!!.id1?.joinToString(",").toString()
                        it.resume(GlobalConstAndVars.PAIRS_lIST)





                        /*converters.converterFromResponseServerToMainList(response.body()!!*/



                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value =
                                AppState.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataToObserve.value =
                                AppState.Error(Throwable(message))
                            it.resumeWithException(Throwable(message))
                        }
                    }
                }
                override fun onFailure(call: Call<ServerResponseData>, t: Throwable) {
                    liveDataToObserve.value = AppState.Error(t)
                    it.resumeWithException(Throwable())
                }
            })
        }
    }


    }
 suspend  fun getCrossCourses():List<ListItem>  {
     return suspendCoroutine {   val x:List<ServerResponseData> = listOf()
         val data:AppState

         val apiKey: String = "39019b3af300a1027141bb1d9eb2354e"
         if (apiKey.isBlank()) {
             AppState.Error(Throwable("You need API key"))
         } else {
             retrofit1C.getRetrofit().getPairs(GlobalConstAndVars.PAIRS_lIST,"39019b3af300a1027141bb1d9eb2354e").enqueue(object :
                 Callback<ServerResponseDataPairs> {
                 override fun onResponse(
                     call: Call<ServerResponseDataPairs>,
                     response: Response<ServerResponseDataPairs>
                 ) {
                     if (response.isSuccessful && response.body() != null) {
                         liveDataToObserve.value =
                             AppState.Success( converters.converterFromStringToMutableListItem(response.body().toString())

                             )
                         it.resume(converters.converterFromStringToMutableListItem(response.body().toString()))




                     } else {
                         val message = response.message()
                         if (message.isNullOrEmpty()) {
                             liveDataToObserve.value =
                                 AppState.Error(Throwable("Unidentified error"))
                         } else {
                             liveDataToObserve.value =
                                 AppState.Error(Throwable(message))
                             it.resumeWithException(Throwable(message))
                         }
                     }
                 }

                 override fun onFailure(call: Call<ServerResponseDataPairs>, t: Throwable) {
                     liveDataToObserve.value = AppState.Error(t)
                     it.resumeWithException(Throwable())
                 }
             })
         } }


    }


    suspend fun getGlobalLIst(){
        createGlobalListCase.getListForChoice()

    }




}
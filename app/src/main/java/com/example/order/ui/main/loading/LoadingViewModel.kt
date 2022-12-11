package com.example.order.ui.main.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.usecase.*
import com.example.order.core.GlobalConstAndVars
import com.example.order.datasource.server.RetrofitService
import com.example.order.datasource.server.ServerResponseData
import com.example.order.datasource.server.ServerResponseDataPairs
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoadingViewModel @Inject constructor (
    private val retrofitService:RetrofitService,
    private val converters: ConvertersUseCase,
    private val createGlobalListCase: CreateListOfAllItemsUseCase = CreateListOfAllItemsDBUseCaseImpl(),
    private val loadFrom1CtoDBCase:LoadDataFromDBUseCase=LoadDataFromDBUseCaseImpl()

):ViewModel() {
    val liveDataToObserve:MutableLiveData<AppState> = MutableLiveData()
    var pairsList=""
    fun getDataFromServerForDB(): LiveData<AppState> {

        return liveDataToObserve
    }
    fun clearDB(){
        loadFrom1CtoDBCase.executeDeletingDataFromDb()
    }
    fun putDataFromServerToLocalDatabase(listFromServer:List<ListItem>){
        loadFrom1CtoDBCase.executeDownloadingDataFromServerToDB(listFromServer)

    }


    suspend fun getCurrenciesPairsList():String  { return suspendCoroutine {
         if (GlobalConstAndVars.API_KEY.isBlank()) {
            AppState.Error(Throwable(GlobalConstAndVars.ERROR_API_KEY_NOT_FOUND))
        } else {
            retrofitService.getRetrofit().getDataFrom1C().enqueue(object :
                Callback<ServerResponseData> {
                override fun onResponse(
                    call: Call<ServerResponseData>,
                    response: Response<ServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        pairsList= response.body()!!.id1?.joinToString(",").toString()
                        it.resume(pairsList)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value =
                                AppState.Error(Throwable())
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
     return suspendCoroutine {
         if (GlobalConstAndVars.API_KEY.isBlank()) {
             AppState.Error(Throwable(GlobalConstAndVars.ERROR_API_KEY_NOT_FOUND))
         } else {
             retrofitService.getRetrofit().getPairs(pairsList,GlobalConstAndVars.API_KEY).enqueue(object :
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
                                 AppState.Error(Throwable())
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
        createGlobalListCase.getCurrenciesList()

    }




}
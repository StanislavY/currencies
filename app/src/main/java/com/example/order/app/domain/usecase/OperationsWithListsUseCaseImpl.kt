package com.example.order.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem

class OperationsWithListsUseCaseImpl: OperationsWithListsUseCase {
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getMainList(): List<ListItem> {
        return  GlobalConstAndVars.GLOBAL_LIST


                }

            }









package com.example.order.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.example.order.core.App

class FavoriteLogicUseCasempl: FavoriteLogicUseCase {
    private val localDataSource: LocalRepository = LocalRepositoryImpl(App.get1CDAO())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun executeAddingToFavorites(listItem: ListItem): MutableList<ListItem> {
        val rememberedListItem:MutableList<ListItem> = mutableListOf()
         if (listItem.favorite == GlobalConstAndVars.ITS_NOT_FAVORITE) {
            listItem.favorite=GlobalConstAndVars.ITS_FAVORITE
        }
        else {
            listItem.favorite=GlobalConstAndVars.ITS_NOT_FAVORITE
        }
        rememberedListItem.add(listItem)
        GlobalConstAndVars.GLOBAL_LIST.forEach {
            if (it.id1 == listItem.id1&&it.id2==listItem.id2) {
                it.favorite=listItem.favorite
            } }

        GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS=rememberedListItem
        return GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
    }

    override fun executeMakingItemFavorite(data:MutableList<ListItem>) {
        localDataSource.writeFavoriteToDb(data)

    }


}



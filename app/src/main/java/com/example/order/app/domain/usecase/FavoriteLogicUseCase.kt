package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

interface FavoriteLogicUseCase {
    fun executeAddingToFavorites(listItem: ListItem):MutableList<ListItem>
    fun executeMakingItemFavorite(data:MutableList<ListItem>)


}

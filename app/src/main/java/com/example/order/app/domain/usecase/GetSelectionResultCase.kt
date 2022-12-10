package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

interface GetSelectionResultCase {
    fun executeAddingToFavorites(listItem: ListItem):MutableList<ListItem>
    fun makeOrderFinished()
    fun putListOfChosenItemToDB(data:MutableList<ListItem>)
    fun getAllDataDBResultEntityToListItem():List<ListItem>



}

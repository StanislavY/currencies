package com.example.order.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem

class CreateListsForFirstAndSecondScreensCaseImpl: CreateListsForFirstAndSecondScreensCase {
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getMainList(key: String): List<ListItem> {

    val rememberedListItem:MutableList<ListItem> = GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
        val listFrom1C= GlobalConstAndVars.GLOBAL_LIST
        val tempListItem:MutableList<ListItem> = mutableListOf()
        for (mainList in listFrom1C) {
            /*if (mainList.id1 == key) {*/
               tempListItem.add(mainList)
                /*if (key == GlobalConstAndVars.DEFAULT_VALUE) {
                    mainList.value=""

                }*/
                               }
       /* }*/
              /*  for (mainList in tempListItem) {
                    for (remList in rememberedListItem) {
                        if (mainList.id2 == remList.id1) {
                            mainList.value=remList.name

                    }

                    }

                }*/
        GlobalConstAndVars.LIST_OF_ITEMS_FOR_FIRST_AND_SECOND_SCREENS=tempListItem/*.distinctBy { it.name to it.id1 to it.id2 }*/
        return  GlobalConstAndVars.GLOBAL_LIST


                }

            }









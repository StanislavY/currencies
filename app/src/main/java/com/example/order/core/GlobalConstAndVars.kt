package com.example.order.core

import com.example.order.app.domain.model.ListItem
import com.example.order.datasource.Server.ServerResponseData

object GlobalConstAndVars {
    const val DEFAULT_VALUE: String = "0"
    var listItemFromDb:List<ListItem> = mutableListOf()
    var LIST_KEY: String = DEFAULT_VALUE
    var count: Int = 0
    var KEY_FOR_INFLATE_MAIN_LIST = 0
    var LIST_OF_CHOSEN_ITEMS: MutableList<ListItem> = mutableListOf()
    var DATABASE1C_NAME: String = "Database1C.db"
    /*const val STEP_FOR_WORK_LIST=0.01*///не удалять - может понадобиться
    /*const val NUMBERS_OF_VALUES_FOR_WORK_LIST=3000*///не удалять - может понадобиться
    const val STEP_FOR_WORKED_HOURS=1.0
    const val NUMBERS_OF_VALUES_FOR_WORKED_HOURS=50
    val DEFAULT_lIST= listOf(ListItem("","","",""))
    var DATE_OF_ORDER=""
    var GLOBAL_LIST= DEFAULT_lIST
    var LIST_OF_ITEMS_FOR_FIRST_AND_SECOND_SCREENS:List<ListItem> = mutableListOf()
    const val DEFAULT_VALUE_FOR_GENERATED_LIST="0"
    var LIST_OF_FINISHED_ORDERS = listOf<ServerResponseData>()
    const val MARKER_OF_FINISHED_ORDER="1"
    var WORKED_OUT:String=""
    var SWITCH_FOR_ORDERS_LIST=0

}
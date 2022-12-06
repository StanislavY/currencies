package com.example.order.app.domain.model

import android.text.Spannable
import android.text.SpannableString

data class SearchItem (
   val id1:Spannable,
   val id2:Spannable,
   val name: Spannable,
   val value:Spannable,
   var secondCurFlag:Spannable,
   var countryFirstCur:Spannable,
   var countrySecondCur:Spannable,
   var favorite:Spannable

) {
    constructor(id1:String, id2:String, name: String,value:String, secondCurFlag:String,
                countryFirstCur:String,
               countrySecondCur:String,
                curName:String) : this(SpannableString(id1), SpannableString(id2), SpannableString(name),SpannableString(value),
        SpannableString(secondCurFlag),SpannableString(countryFirstCur),SpannableString(countrySecondCur),SpannableString(curName),)
}

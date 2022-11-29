package com.example.order.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.order.R
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.model.SearchItemStorage.Companion.list
import com.example.order.core.App
import com.example.order.databinding.MainFragmentBinding
import com.example.order.databinding.MainItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.net.URI

class MainFragmentAdapter():RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var appContext= App.getContext()
    private var listItemData: List<ListItem> = listOf()
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener? = null
    private var resID: Int=0


    @SuppressLint("NotifyDataSetChanged")
    fun setListItem(data: List<ListItem>) {
        listItemData = data
        notifyDataSetChanged()

    }

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemViewClickListener = null
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listItemData[position])
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MainViewHolder {



        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return MainViewHolder(binding)
    }


    override fun getItemCount() = listItemData.size
    inner class MainViewHolder(private val binding: MainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(listItem: ListItem) {



            val textForItem:String = listItem.id1+"/"+listItem.id2+" "+listItem.value/*if (GlobalConstAndVars.LIST_KEY == "0"&&GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST==0) {


                    listItem.name + ": " + listItem.value


            }


            else {
                if (listItem.id1 == "ВР"&&listItem.value!="") {
                    listItem.value + ": " + listItem.name
                } else {
                   listItem.name
                }
            }
            if (GlobalConstAndVars.LIST_KEY == "0"&&GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST==1) {
                listItem.id2

            }*/


            binding.apply {

                if (listItem.name == "0") {
                    listItem.name= R.drawable.ad333.toString()

                } else {
                        listItem.name =appContext?.resources?.getIdentifier(listItem.
                        name, "drawable", appContext?.packageName).toString()

                }
               flagOfFirstCur.setImageResource(
                   listItem.
                   name.toInt())

                ticker.text="1 ${listItem.id1}"
                quantitySecondCur.text=listItem.value
                tickerSecondCur.text=listItem.id2





                binding.favoriteButton.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(listItem)
                }
            }

        }


    }

   /* private fun getFlag( parent: MainFragmentBinding,mDrawableName:String):Int{


        resID = parent.context.resources.getIdentifier(mDrawableName, "drawable",parent.context.packageName)

       return resID
    }*/
}
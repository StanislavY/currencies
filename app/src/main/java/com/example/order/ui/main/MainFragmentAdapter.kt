package com.example.order.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.order.R
import com.example.order.app.domain.model.ListItem
import com.example.order.core.App
import com.example.order.databinding.MainItemBinding

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

                listItem.name=writeFileIDtoList(listItem.name)
                listItem.secondCurFlag=writeFileIDtoList(listItem.secondCurFlag)
                flagOfFirstCur.setImageResource(listItem.name.toInt())
                tickerFirstCur.text="1 ${listItem.id1}"
                quantitySecondCur.text=listItem.value
                tickerSecondCur.text=listItem.id2
                flagOfSecondCur.setImageResource(listItem.secondCurFlag.toInt())






                binding.favoriteButton.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(listItem)
                }
            }

        }


    }

    private fun writeFileIDtoList(listItem: String):String {
        val fileID: String = if (listItem == "0") {
            R.drawable.ad333.toString()

        } else {
            appContext?.resources?.getIdentifier(listItem,"drawable",
                appContext?.packageName).toString()

        }
        return fileID
    }

    /* private fun getFlag( parent: MainFragmentBinding,mDrawableName:String):Int{


         resID = parent.context.resources.getIdentifier(mDrawableName, "drawable",parent.context.packageName)

        return resID
     }*/
}
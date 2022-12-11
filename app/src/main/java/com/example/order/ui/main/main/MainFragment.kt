package com.example.order.ui.main.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.order.R
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.model.SearchItemStorage
import com.example.order.app.domain.usecase.AppState
import com.example.order.core.GlobalConstAndVars
import com.example.order.databinding.MainFragmentBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var entryCount=0

    private var filterAbc = 0
    private var filter123 = 0


    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = MainFragmentAdapter()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeOnItemViewClickListener()

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setLayoutParams()
        tab_main.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateSearch()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
        binding.apply {
            abcFilter.setOnClickListener {
            SearchItemStorage.list=viewModel.convertMainListToArrayListItem(sortAbc(viewModel.convertArrayListItemToListItem(SearchItemStorage.list)))
            updateSearch()}
            valueFilter.setOnClickListener {
                SearchItemStorage.list=viewModel.convertMainListToArrayListItem(sort123(viewModel.convertArrayListItemToListItem(SearchItemStorage.list)))
                updateSearch()
            }
        }
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {

               viewModel.handleFavoriteButtonClick(listItem)
                viewModel.makeItemFavoriteInDB(viewModel.getListOfChosenItems())
                SearchItemStorage.list =viewModel.convertMainListToArrayListItem(viewModel.getGlobalList())
                updateSearch()
            }
        })

        binding.mainFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainFragmentRecyclerView.adapter = adapter
        viewModel.processAppState().observe(viewLifecycleOwner, { renderList(it) })
        viewModel.getMainList()
        launchSearchBarListener()


    }


    private fun sortAbc(listToSort:List<ListItem>):List<ListItem> {

        if (filterAbc == 0) {
            binding.abcFilter.setImageResource(R.drawable.sort_abc_down)
            filterAbc = 1
           return viewModel.setGlobalList(listToSort.sortedByDescending { it.id1 })

        } else
            filterAbc = 0
        binding.abcFilter.setImageResource(R.drawable.sort_abc_up)
       return viewModel.setGlobalList(listToSort.sortedBy { it.id1 })
    }




    private fun sort123(listToSort: List<ListItem>): List<ListItem> {


        if (filter123 == 0) {
            filter123 = 1
            binding.valueFilter.setImageResource(R.drawable.sort_123_down)

            return  viewModel.setGlobalList(viewModel.convertValueToStringInListItem(viewModel.convertValueToDoubleInListItem(listToSort).sortedByDescending { it.value }))

        } else
            filter123 = 0
        binding.valueFilter.setImageResource(R.drawable.sort_123_up)

        return viewModel.setGlobalList(viewModel.convertValueToStringInListItem(viewModel.convertValueToDoubleInListItem(listToSort).sortedBy { it.value }))
    }


    private fun updateSearch():List<ListItem> {

        val etSearchBar = binding.inputEditText
        val s = etSearchBar.text
        val listToFilter=viewModel.convertArrayListItemToListItem(SearchItemStorage.list)
        if (s?.length == 0&&tab_main.selectedTabPosition==0) {
        viewModel.sendDataToList(listToFilter)
            return listToFilter
        }
        if (s?.length !=0&&tab_main.selectedTabPosition==0) {
            viewModel.sendDataToList(filterList(listToFilter,s))
                   return filterList(listToFilter,s)
            }
        if (s?.length == 0&&tab_main.selectedTabPosition==1) {
            viewModel.sendDataToList(listToFilter.filter { it.favorite == GlobalConstAndVars.ITS_FAVORITE })

           return listToFilter.filter { it.favorite == GlobalConstAndVars.ITS_FAVORITE}
        }
        if (s?.length !=0&&tab_main.selectedTabPosition==1) {
            viewModel.sendDataToList(filterList(listToFilter.filter { it.favorite == GlobalConstAndVars.ITS_FAVORITE },s))

            return filterList(listToFilter.filter { it.favorite == GlobalConstAndVars.ITS_FAVORITE },s)
            }

        return GlobalConstAndVars.DEFAULT_lIST
    }


    private fun filterList(listToFilter:List<ListItem>,s:Editable?):List<ListItem>{

        return  listToFilter.filter {

            it.id2.contains(s.toString(), true)
                    || it.id1.contains(s.toString(), true)
                    || it.countryFirstCur.contains(s.toString(), true)
                    || it.countrySecondCur.contains(s.toString(), true)
                    || it.favorite.contains(s.toString(), true)

        }

    }



    private fun renderList(data: AppState) {
        when (data) {
            is AppState.Success -> {
               adapter.setListItem(data.listItem)
                if (entryCount==0){
                    SearchItemStorage.list=viewModel.convertMainListToArrayListItem(data.listItem)
                    entryCount++
                    }
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
                Toast.makeText(context, data.error.message, Toast.LENGTH_LONG).
                show()
            }

        }

    }


    interface OnItemViewClickListener {
        fun onItemViewClick(listItem: ListItem)
    }


    private fun setLayoutParams(){
        val params = binding.mainFragmentRecyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom=binding.inputLayout.id
        params.matchConstraintPercentHeight= 0.79F

    }

    private fun launchSearchBarListener(){
        val etSearchBar=binding.inputEditText
        etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearch()
            }
        })
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "2"
        fun newInstance(sectionNumber: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
        }

 }





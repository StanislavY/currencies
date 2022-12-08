package com.example.order.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.order.R
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.model.SearchItemStorage
import com.example.order.app.domain.usecase.AppState
import com.example.order.core.GlobalConstAndVars
import com.example.order.core.GlobalConstAndVars.KEY_FOR_INFLATE_MAIN_LIST
import com.example.order.core.GlobalConstAndVars.count
import com.example.order.databinding.MainFragmentBinding
import com.example.order.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import java.util.*


class MainFragment : Fragment() {
    var filterAbc = 0
    var filter123 = 0
    var sortMode=0


    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = MainFragmentAdapter()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    data class ListItemWithDoubles(
        var id1: String,
        var id2: String,
        var name: String,
        var value: Double,
        var secondCurFlag: String,
        var countryFirstCur: String,
        var countrySecondCur: String,
        var favorite: String
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentBinding.inflate(inflater, container, false)


//        val sectionsPagerAdapter = SectionsPagerAdapter(this, parentFragmentManager)
//        val viewPager: ViewPager = binding.viewPager
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = binding.tabMain
//        tabs.setupWithViewPager(viewPager)

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeOnItemViewClickListener()


    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setWorkedOutFieldBehavior()

        setRecyclerParams()
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
            SearchItemStorage.list=viewModel.convertMainListToArrayListItem(sortAbc(viewModel.convertArrayListItemToMainList(SearchItemStorage.list)))
            updateSearch()}

            valueFilter.setOnClickListener {
                SearchItemStorage.list=viewModel.convertMainListToArrayListItem(sort123(viewModel.convertArrayListItemToMainList(SearchItemStorage.list)))
                updateSearch()
            }
        }
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {
                viewModel.handleFavoriteButtonClick(listItem)
                viewModel.putDataToResultDB(GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS)
                SearchItemStorage.list =viewModel.convertMainListToArrayListItem(GlobalConstAndVars.GLOBAL_LIST)
                updateSearch()


            }
        })

        binding.mainFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainFragmentRecyclerView.adapter = adapter
        viewModel.processAppState().observe(viewLifecycleOwner, { renderList(it) })
        viewModel.processTheSelectedItem()
        launchSearchBarListener()
        isEditingWorkedOutFieldFinished()

    }

    private fun favoriteButtonClicked(listItem: ListItem) {
        viewModel.handleFavoriteButtonClick(listItem)
        checkFieldsCompleteness()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main_botom_bar, menu)
    }

    private fun setBottomAppBar() {
        val context = activity as MainActivity
        /* context.setSupportActionBar(binding.bottomBarMain)*/
        setHasOptionsMenu(true)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* if (item.itemId == R.id.favorite_app_bottom_bar) {
            rememberDate()
            rememberWorkedOutAmount()

            goToSaveFragment(activity?.supportFragmentManager)
        }
        if (item.itemId == R.id.refresh) {
            sendDataToServer()

        }
        if (item.itemId == R.id.order_list) {

            val manager = activity?.supportFragmentManager
            val listItem=ListItem("","","","","","","","")
            showOrHideOrdersList()

           makeDetails(manager,listItem)

        }*/


        return super.onOptionsItemSelected(item)
    }

    private fun showOrHideOrdersList() {
        if (GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST == 0) {
            GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST = 1
            /*viewModel.getOrdersListFromDBResult()*/
            /* newInstance()*/

        } else {
            GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST = 0
            /*  viewModel.getGlobalLIst()*/
            /*newInstance()*/
        }
    }


    private fun checkFieldsCompleteness() {

        viewModel.putDataToResultDB(GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS)

    }

    private fun setDefaultValuesForTheGlobalVars(workedOut: TextInputEditText) {
        GlobalConstAndVars.LIST_OF_ITEMS_FOR_FIRST_AND_SECOND_SCREENS = mutableListOf()
        GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS = mutableListOf()
        GlobalConstAndVars.DATE_OF_ORDER = ""
        GlobalConstAndVars.LIST_KEY = GlobalConstAndVars.DEFAULT_VALUE
        GlobalConstAndVars.WORKED_OUT = ""
        workedOut.setText(GlobalConstAndVars.WORKED_OUT)
    }

    private fun sendDataToServer() {
        val listToSend = viewModel.getAllDataDBResultEntityToListItem()
        if (listToSend.isEmpty())
            toast("Ничего не загружено, т к все данные уже были загружены ранее")
        else {
            viewModel.pullDataToServer(viewModel.getAllDataDBResultEntityToListItem())
            viewModel.processAppState().observe(viewLifecycleOwner, { isDataUploadedToServer(it) })

        }
    }

    private fun rememberWorkedOutAmount() {
        if (GlobalConstAndVars.WORKED_OUT != "") {
            val worked = ListItem(
                "Фактически отработано в натуре",
                GlobalConstAndVars.WORKED_OUT,
                GlobalConstAndVars.WORKED_OUT,
                GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST,
                "",
                "",
                "",
                "") // убрать хардкод из этой строки
            viewModel.rememberListOfChosenItemsVM(worked)
        }
    }

    private fun rememberDate() {
        if (GlobalConstAndVars.DATE_OF_ORDER != "") {
            val dateFromCalendar = ListItem(
                "date",
                "date",
                GlobalConstAndVars.DATE_OF_ORDER,
                "", "", "", "", "Ариари")//брать хардкод из этой строки
            viewModel.rememberListOfChosenItemsVM(dateFromCalendar)
        }
    }

    private fun makeDetails(
        manager: FragmentManager?,
        listItem: ListItem
    ) {
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, listItem)
            manager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance(bundle))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }


    private fun sortAbc(listToSort:List<ListItem>):List<ListItem> {

        if (filterAbc == 0) {
            binding.abcFilter.setImageResource(R.drawable.sort_abc_down)
            filterAbc = 1
           return listToSort.sortedByDescending { it.id1 }


        } else
            filterAbc = 0
        binding.abcFilter.setImageResource(R.drawable.sort_abc_up)
       return listToSort.sortedBy { it.id1 }







    }

    private fun sort123(listToSort: List<ListItem>): List<ListItem> {
        if (filter123 == 0) {
            filter123 = 1
            binding.valueFilter.setImageResource(R.drawable.sort_123_down)
            return convertFromDouble(convertToDouble(listToSort).sortedByDescending { it.value })

        } else
            filter123 = 0
        binding.valueFilter.setImageResource(R.drawable.sort_123_up)
        return convertFromDouble(convertToDouble(listToSort).sortedBy { it.value })


    }
    private fun convertToDouble(list:List<ListItem>):List<ListItemWithDoubles>{


       return list.map {
            ListItemWithDoubles(it.id1,
                it.id2,
                it.name,
                it.value.toDouble(),
                it.secondCurFlag,
                it.countryFirstCur,
                it.countrySecondCur,
                it.favorite)
        }
    }
    private fun convertFromDouble(list:List<ListItemWithDoubles>):List<ListItem>{
        return list.map {
            ListItem(it.id1,
                it.id2,
                it.name,
                it.value.toString(),
                it.secondCurFlag,
                it.countryFirstCur,
                it.countrySecondCur,
                it.favorite)
        }
    }





    private fun updateSearch():List<ListItem> {


        val etSearchBar = binding.inputEditText
        val s = etSearchBar.text
        val listToFilter=viewModel.convertArrayListItemToMainList(SearchItemStorage.list)
        if (s?.length == 0&&tab_main.selectedTabPosition==0) {
           adapter.setListItem(listToFilter)
            return viewModel.convertArrayListItemToMainList(SearchItemStorage.list)
        }
        if (s?.length !=0&&tab_main.selectedTabPosition==0) {
               adapter.setListItem(filterList(listToFilter,s)

                )
                return filterList(listToFilter,s)
            }
        if (s?.length == 0&&tab_main.selectedTabPosition==1) {
            adapter.setListItem(listToFilter.filter { it.favorite == "1" })
           return viewModel.convertArrayListItemToMainList(SearchItemStorage.list).filter { it.favorite == "1" }
        }
        if (s?.length !=0&&tab_main.selectedTabPosition==1) {
             adapter.setListItem(filterList(listToFilter.filter { it.favorite == "1" },s)
                )
            return filterList(listToFilter.filter { it.favorite == "1" },s)
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
                SearchItemStorage.list=viewModel.convertMainListToArrayListItem(data.listItem)
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
                toast(data.error.message)

            }

        }

    }



    private fun isDataUploadedToServer(data: AppState) {
        when (data) {
            is AppState.Success -> {
             toast("Выгрузка прошла успешно")
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
                toast("Данные не загружены:${data.error.message}")


            }

        }

    }

    interface OnItemViewClickListener {
        fun onItemViewClick(listItem: ListItem)
    }

   private fun Fragment.toast(string: String?) {
     fun handleError() {}
       val fragmentCoroutineScope = CoroutineScope(
           Dispatchers.Main+ SupervisorJob() + CoroutineExceptionHandler { _, _ -> handleError() })

        fragmentCoroutineScope.launch { Toast.makeText(context, string, Toast.LENGTH_LONG).
        show() }

    }
    private fun addZeroToMonthAndDay(dayOrMonth:Int):String{
        return if (dayOrMonth <10) {
            "0$dayOrMonth"

        } else{
            dayOrMonth.toString()
        }

    }
    private fun setRecyclerParams(){
        val params = binding.mainFragmentRecyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom=binding.inputLayout.id
        params.matchConstraintPercentHeight= 0.79F

    }
    private fun goToSaveFragment(
        manager: FragmentManager?,

        ) {
        manager?.beginTransaction()?.replace(R.id.container, SaveFragment.newInstance())
            ?.addToBackStack("")?.commitAllowingStateLoss()
    }
    private fun createCalendar() {


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val monthFromCalendar = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
      /*  textView.setText(GlobalConstAndVars.DATE_OF_ORDER)
        input_date_layout.setEndIconOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { _, year, _, dayOfMonth ->
                val month = monthFromCalendar + 1
                GlobalConstAndVars.DATE_OF_ORDER =
                    "$year.${addZeroToMonthAndDay(month)}.${addZeroToMonthAndDay(dayOfMonth)}"
                textView.setText(GlobalConstAndVars.DATE_OF_ORDER)
            }, year, monthFromCalendar, day)
            dpd.show()
        }*/
    }

    private fun setWorkedOutFieldBehavior() {

       /* workedOut.setText(GlobalConstAndVars.WORKED_OUT)
        workedOut.setOnClickListener {
            GlobalConstAndVars.WORKED_OUT = workedOut.text.toString()
            workedOut.setText(GlobalConstAndVars.WORKED_OUT)
        }*/

    }
    private fun isEditingWorkedOutFieldFinished() {

    }
    fun chooseScreenToShow(listItem:ListItem){
        if (count == KEY_FOR_INFLATE_MAIN_LIST) {
            /*binding.inputEditTextDate.hide()*/
            GlobalConstAndVars.LIST_KEY = listItem.id2
            count += 1
            val manager = activity?.supportFragmentManager
            makeDetails(manager, listItem)

        } else {
            /*binding.inputEditTextDate.show()*/
            count = KEY_FOR_INFLATE_MAIN_LIST
            GlobalConstAndVars.LIST_KEY = GlobalConstAndVars.DEFAULT_VALUE
            val manager = activity?.supportFragmentManager
            viewModel.rememberListOfChosenItemsVM(listItem)
            makeDetails(manager, listItem)
        }
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





package com.example.order.ui.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_item.view.*
import kotlinx.coroutines.*
import java.util.*


class MainFragment : Fragment() {



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



        createCalendar()
        setWorkedOutFieldBehavior()
        /*setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))*/
        setBottomAppBar()
        hideUnnecessaryFields()
         adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(listItem: ListItem) {
                viewModel.handleFavoriteButtonClick(listItem)
                viewModel.putDataToResultDB(GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS)
                adapter.setListItem(GlobalConstAndVars.GLOBAL_LIST)


            }
        })

        binding.mainFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainFragmentRecyclerView.adapter = adapter
        viewModel.processAppState().observe(viewLifecycleOwner, { renderList(it) })
        viewModel.processTheSelectedItem()
        launchSearchBarListener()
        isEditingWorkedOutFieldFinished()    

    }

    private fun favoriteButtonClicked(listItem:ListItem) {
        viewModel.handleFavoriteButtonClick(listItem)
        checkFieldsCompleteness()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main_botom_bar, menu)
    }

    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomBarMain)
        setHasOptionsMenu(true)

    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.send_main_bottom_bar) {
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

        }


        return super.onOptionsItemSelected(item)
    }

    private fun showOrHideOrdersList() {
        if (GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST == 0) {
            GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST=1
            /*viewModel.getOrdersListFromDBResult()*/
            newInstance()

        }
        else { GlobalConstAndVars.SWITCH_FOR_ORDERS_LIST=0
          /*  viewModel.getGlobalLIst()*/
            newInstance()
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
                GlobalConstAndVars.DEFAULT_VALUE_FOR_GENERATED_LIST
                ,"","","","") // убрать хардкод из этой строки
            viewModel.rememberListOfChosenItemsVM(worked)
        }
    }

    private fun rememberDate() {
        if (GlobalConstAndVars.DATE_OF_ORDER != "") {
            val dateFromCalendar = ListItem(
                "date",
                "date",
                GlobalConstAndVars.DATE_OF_ORDER,
                "","","","","Ариари")//брать хардкод из этой строки
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
    private fun updateSearch() {
        val etSearchBar=binding.inputEditText
        val s = etSearchBar.text
        if (s?.length == 0) {
            adapter.setListItem(viewModel.convertArrayListItemToMainList(SearchItemStorage.list))
        } else {
            adapter.setListItem( viewModel.convertArrayListItemToMainList(SearchItemStorage.list).filter {

                it.id2.contains(s.toString(), true)
                        ||it.id1.contains(s.toString(), true)
                        ||it.countryFirstCur.contains(s.toString(),true )
                        ||it.countrySecondCur.contains(s.toString(),true )
                        ||it.favorite.contains(s.toString(),true )
            }
            )


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
    private fun hideUnnecessaryFields(){
        binding.inputEditTextDate.isGone=true
        binding.bottomBarMain.isGone=true
        binding.inputLayout.isGone=false
        binding.inputEditTextWorkedOut.isGone=true
        binding.inputDateLayout.endIconMode=TextInputLayout.END_ICON_NONE
        val params = binding.mainFragmentRecyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom=binding.inputLayout.id
        params.matchConstraintPercentHeight= 0.89F
        binding.bottomBarMain.isGone=false

       /* if (count!= KEY_FOR_INFLATE_MAIN_LIST) {
            //второй экран
            binding.inputEditTextDate.isGone=true
            binding.bottomBarMain.isGone=true
            binding.inputLayout.isGone=false
            binding.inputEditTextWorkedOut.isGone=true
            binding.inputDateLayout.endIconMode=TextInputLayout.END_ICON_NONE
            val params = binding.mainFragmentRecyclerView.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom=binding.inputLayout.id
            params.matchConstraintPercentHeight= 0.89F

        } else {
            //первый экран
            binding.inputDateLayout.isGone=false
            binding.inputLayout.isGone=true
            binding.bottomBarMain.isGone=false
            binding.inputEditTextWorkedOut.isGone=false

        }*/
    }
    private fun goToSaveFragment(
        manager: FragmentManager?,

        ) {
        manager?.beginTransaction()?.replace(R.id.container, SaveFragment.newInstance())
            ?.addToBackStack("")?.commitAllowingStateLoss()
    }
    private fun createCalendar() {

        val textView = binding.inputEditTextDate
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val monthFromCalendar = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        textView.setText(GlobalConstAndVars.DATE_OF_ORDER)
        input_date_layout.setEndIconOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { _, year, _, dayOfMonth ->
                val month = monthFromCalendar + 1
                GlobalConstAndVars.DATE_OF_ORDER =
                    "$year.${addZeroToMonthAndDay(month)}.${addZeroToMonthAndDay(dayOfMonth)}"
                textView.setText(GlobalConstAndVars.DATE_OF_ORDER)
            }, year, monthFromCalendar, day)
            dpd.show()
        }
    }

    private fun setWorkedOutFieldBehavior() {
        val workedOut = binding.inputEditTextWorkedOut
        workedOut.setText(GlobalConstAndVars.WORKED_OUT)
        workedOut.setOnClickListener {
            GlobalConstAndVars.WORKED_OUT = workedOut.text.toString()
            workedOut.setText(GlobalConstAndVars.WORKED_OUT)
        }

    }
    private fun isEditingWorkedOutFieldFinished() {
        binding.inputEditTextWorkedOut.setOnFocusChangeListener { _: View, b: Boolean ->
            if (!b) {
                GlobalConstAndVars.WORKED_OUT = binding.inputEditTextWorkedOut.text.toString()
                binding.inputEditTextWorkedOut.setText(GlobalConstAndVars.WORKED_OUT)
            }
        }
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
        fun newInstance()= MainFragment()
        }
 }





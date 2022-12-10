package com.example.order.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.order.R
import com.example.order.app.domain.usecase.AppState
import com.example.order.databinding.LoadingFragmentBinding
import com.example.order.viewModel.LoadingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
@AndroidEntryPoint

class LoadingFragment:Fragment() {
    private var _binding:LoadingFragmentBinding?=null
    private val binding get()=_binding!!
    private val viewModel:LoadingViewModel by lazy { ViewModelProvider(this).get(LoadingViewModel::class.java) }
    private val loadingFragmentCoroutineScope =
        CoroutineScope(Dispatchers.Default+ SupervisorJob() + CoroutineExceptionHandler{ _, _ -> })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= LoadingFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadinglayout.show()
        viewModel.getDataFromServerForDB().observe(viewLifecycleOwner, { handleData(it) })
        runBlocking { loadingFragmentCoroutineScope.launch {
            viewModel.getCurenciesPairsList()
            viewModel.getCrossCourses()

             }
        }
    }


   private fun handleData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                viewModel.clearDB()
                viewModel.putDataFromServerToLocalDatabase(data.listItem)
                getCurrenciesList()
                Toast.makeText(context,"Котировки загружены успешно",Toast.LENGTH_SHORT).show()
                binding.loadinglayout.hide()
                goToMainList(activity?.supportFragmentManager)

            }
            is AppState.Loading -> {
                binding.loadinglayout.show()
            }
            is AppState.Error -> {
                binding.loadinglayout.show()
                getCurrenciesList()
                Toast.makeText(context,"Нет доступа к серверу, загруженные последние доступные котировки",Toast.LENGTH_SHORT).show()

                goToMainList(activity?.supportFragmentManager)


            }
        }
    }
    private fun getCurrenciesList() = runBlocking { val job = launch {viewModel.getGlobalLIst()  }
        job.join()

    }


    private fun goToMainList(
        manager: FragmentManager?,

    ) {
        manager?.beginTransaction()?.replace(R.id.container, MainFragment.newInstance(1))
            ?.addToBackStack("")?.commitAllowingStateLoss()
    }


    companion object {
        fun newInstance() = LoadingFragment()

        }

    }







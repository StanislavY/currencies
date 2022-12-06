package com.example.order.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.order.app.domain.usecase.AppState
import com.example.order.R
import com.example.order.viewModel.SaveViewModel
import com.example.order.databinding.SaveFragmentBinding

class SaveFragment:Fragment() {
    private var _binding:SaveFragmentBinding?=null
    private val binding get()=_binding!!
    private val viewModel: SaveViewModel by lazy { ViewModelProvider(this)[SaveViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= SaveFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveLayout.show()
        viewModel.getDataFromServerForDB().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.awaiting()
        viewModel.getGlobalLIst()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                binding.saveLayout.hide()
                goToMainList(activity?.supportFragmentManager)
            }
            is AppState.Loading -> {
                binding.saveLayout.show()
                Thread.sleep(5000)
            }
            is AppState.Error -> {
                binding.saveLayout.show()
                Toast.makeText(context,data.error.message,Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.BOTTOM,0,250)
                    show()
                }
                goToMainList(activity?.supportFragmentManager)

            }
        }
    }

    private fun goToMainList(
        manager: FragmentManager?,

    ) {
        manager?.beginTransaction()?.replace(R.id.container, MainFragment.newInstance(1))
            ?.addToBackStack("")?.commitAllowingStateLoss()
    }
    companion object {
        fun newInstance() = SaveFragment()

        }
    }







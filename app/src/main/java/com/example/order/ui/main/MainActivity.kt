package com.example.order.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.order.databinding.MainActivityBinding
import com.example.order.ui.main.loading.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, LoadingFragment.newInstance())
                .commitNow()
        }
    }
}
package com.example.shoppingapp

import com.example.shoppingapp.adapter.ItemsRecyclerViewAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.model_or_schema.DataModel
import com.example.shoppingapp.viewmodels.MainViewModel
import com.example.shoppingapp.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemsRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initApp()
    }

    private fun initApp() {
        setAdapter()
        initObserver()
    }

    private fun setAdapter() {
        adapter = ItemsRecyclerViewAdapter()
        binding.itemsRv.adapter = adapter
    }

    private fun initObserver() {
        val repository = (application as ItemApplication).itemRepository

        val mainViewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(repository)
            )[MainViewModel::class.java]

        mainViewModel.itemsList.observe(this) {
            adapter.items = it as ArrayList<DataModel>
            adapter.notifyDataSetChanged()
        }
    }
}
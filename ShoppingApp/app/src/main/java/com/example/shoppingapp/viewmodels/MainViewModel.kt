package com.example.shoppingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model_or_schema.DataModel
import com.example.shoppingapp.repository.ItemsRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ItemsRepository
) : ViewModel() {

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            repository.getItems()
        }
    }


    val itemsList: LiveData<List<DataModel>>
        get() = repository.items
}
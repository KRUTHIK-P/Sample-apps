package com.example.shoppingapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppingapp.model_or_schema.DataModel
import com.example.shoppingapp.db.ItemsDatabase

class ItemsRepository (
    private val itemsDatabase: ItemsDatabase
) {

    private val itemsLiveData = MutableLiveData<List<DataModel>>()

    val items: LiveData<List<DataModel>>
    get() = itemsLiveData

    suspend fun getItems() {
        val itemsList = itemsDatabase.itemsDao().getItems()
        itemsLiveData.postValue(itemsList)

    }

}







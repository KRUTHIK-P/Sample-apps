package com.example.shoppingapp

import android.app.Application
import com.example.shoppingapp.db.ItemsDatabase
import com.example.shoppingapp.repository.ItemsRepository

class ItemApplication: Application() {

    lateinit var itemRepository: ItemsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val database = ItemsDatabase.getDatabase(applicationContext)
        itemRepository = ItemsRepository(database)
    }
}
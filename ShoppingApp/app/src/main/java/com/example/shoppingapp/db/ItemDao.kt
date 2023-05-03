package com.example.shoppingapp.db

import androidx.room.*
import com.example.shoppingapp.model_or_schema.DataModel

@Dao
interface ItemDao {

    @Query("SELECT * FROM Item")
    suspend fun getItems(): List<DataModel>

    @Update
    suspend fun update(item: DataModel)
}
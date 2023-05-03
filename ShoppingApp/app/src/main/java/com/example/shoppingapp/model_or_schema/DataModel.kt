package com.example.shoppingapp.model_or_schema

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item")
data class DataModel(
    @PrimaryKey//(autoGenerate = true)
    val id: Int,
    val item: String,
    var count: Int
)

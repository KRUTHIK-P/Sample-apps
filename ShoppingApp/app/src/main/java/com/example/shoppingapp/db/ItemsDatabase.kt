package com.example.shoppingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppingapp.model_or_schema.DataModel

// for room db
@Database(entities = [DataModel::class], version = 1) // step 1
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemDao // step 2

    companion object { // step 3
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getDatabase(context: Context): ItemsDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ItemsDatabase::class.java,
                        "itemsDB"
                    ).createFromAsset("database/items.db").build()
                }
            }
            return INSTANCE!!
        }
    }
}
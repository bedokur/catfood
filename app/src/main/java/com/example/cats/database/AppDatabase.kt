package com.example.cats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cats.data.FedData
import com.example.cats.data.FoodItem
import com.example.cats.data.TimeData

@Database(
    entities = [FedData::class, TimeData::class, FoodItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun FedDao(): FedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fed-table"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
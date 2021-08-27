package com.example.cats.database

import androidx.room.*
import com.example.cats.data.FedData
import com.example.cats.data.FoodItem
import com.example.cats.data.TimeData
import kotlinx.coroutines.flow.Flow

@Dao
interface FedDao {
    @Query("select * from fed_table")
    fun getAllList(): Flow<List<FedData>>

    @Insert
    suspend fun insertFedData(fed: FedData)

    @Query("select * from time_table")
    fun getTime(): Flow<TimeData>

    @Insert
    suspend fun insertTime(timeData: TimeData)

    @Query("delete from time_table")
    suspend fun deleteTimeTable()

    @Query("select * from foodName_table")
    fun getAllFoodNames(): Flow<List<FoodItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFoodName(food: FoodItem)

    @Delete
    suspend fun deleteFoodName(vararg food: FoodItem)
}
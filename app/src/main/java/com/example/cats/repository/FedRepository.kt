package com.example.cats.repository

import com.example.cats.data.FedData
import com.example.cats.data.FoodItem
import com.example.cats.data.TimeData
import com.example.cats.database.FedDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FedRepository constructor(private val FedDao: FedDao) {

    val allFedData: Flow<List<FedData>> = FedDao.getAllList()

    suspend fun insertFedData(fed: FedData) {
        FedDao.insertFedData(fed)
    }

    val timerTime: Flow<TimeData> = FedDao.getTime()

    suspend fun insertTime(time: TimeData) {
        FedDao.insertTime(time)
    }

    suspend fun deleteTime() {
        FedDao.deleteTimeTable()
    }

    val foodNames: Flow<List<FoodItem>> = FedDao.getAllFoodNames()

    suspend fun insertFoodName(food: FoodItem){
        FedDao.insertFoodName(food)
    }

    suspend fun deleteFoodName(vararg food: FoodItem){
        FedDao.deleteFoodName(*food)
    }

}
package com.example.cats.viewModels


import androidx.lifecycle.*
import com.example.cats.data.FedData
import com.example.cats.data.FoodItem
import com.example.cats.data.TimeData
import com.example.cats.repository.FedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel (private val repository: FedRepository) : ViewModel() {

    val fedDataList: LiveData<List<FedData>> = repository.allFedData.asLiveData()

    fun insertFedData(fed: FedData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFedData(fed)
    }


    val timerTime: LiveData<TimeData> = repository.timerTime.asLiveData(Dispatchers.IO)

    fun insertTime(timeData: TimeData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTime(timeData)
    }


    fun deleteTimeTable() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTime()
    }

    val foodNames: LiveData<List<FoodItem>> = repository.foodNames.asLiveData(Dispatchers.IO)

    fun insertFoodName(food: FoodItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFoodName(food)
    }

    fun deleteFoodNames(vararg food: FoodItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFoodName(*food)
    }
}


//class SharedViewModelFactory(private val repository: FedRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SharedViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}
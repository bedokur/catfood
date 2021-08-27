package com.example.cats

import android.app.Application
import com.example.cats.database.AppDatabase
import com.example.cats.di.DaggerApplicationComponent
import com.example.cats.repository.FedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CatsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Instance = this
    }

    val appComponent = DaggerApplicationComponent.create()
    
    private val dataBase by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { FedRepository(dataBase.FedDao()) }

    companion object {
        lateinit var Instance: CatsApplication
    }
}
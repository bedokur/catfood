package com.example.cats.di

import androidx.room.Room
import com.example.cats.CatsApplication
import com.example.cats.database.AppDatabase
import com.example.cats.database.FedDao
import dagger.Module
import dagger.Provides


@Module
class MyModule {
    @Provides
    fun provideYourDatabase(): AppDatabase = AppDatabase.getDatabase(
        CatsApplication.Instance
    )

    @Provides
    fun provideFedDao(appDatabase: AppDatabase): FedDao = appDatabase.FedDao()
}

//@Module
//class SimpleModule {
//    @Provides
//    fun providerCooker(): Cooker {
//        return Cooker("tom", "natie")
//    }
//
//    @Provides
//    fun provideCoffeeMaker(cooker: Cooker?): CoffeeMaker {
//        return SimpleMaker(cooker)
//    }
//}
//
//class SimpleMaker @Inject constructor(cooker: Cooker) : CoffeeMaker {
//    var mCooker: Cooker
//    fun makeCoffee(): String {
//        return mCooker.makeCoffee()
//    }
//
//    init {
//        mCooker = cooker
//    }
//}
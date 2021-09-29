package com.example.cats.di

import androidx.room.Room
import com.example.cats.CatsApplication
import com.example.cats.database.AppDatabase
import com.example.cats.database.FedDao
import com.example.cats.repository.FedRepository
import com.example.cats.viewModels.SharedViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MyModule {
    @Singleton
    @Provides
    fun provideYourDatabase(): AppDatabase = AppDatabase.getDatabase(
        CatsApplication.Instance
    )

    @Singleton
    @Provides
    fun provideFedDao(appDatabase: AppDatabase): FedDao = appDatabase.FedDao()

    @Singleton
    @Provides
    fun providesFedRepository(fedDao: FedDao): FedRepository = FedRepository(fedDao)

    @Singleton
    @Provides
    fun providesViewModel(repository: FedRepository): SharedViewModel = SharedViewModel(repository)


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
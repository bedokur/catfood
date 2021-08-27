package com.example.cats.di

import com.example.cats.CatsApplication
import com.example.cats.activities.MainActivity
import com.example.cats.activities.SettingsActivity
import dagger.Component

@Component(modules = [MyModule::class])
interface ApplicationComponent {
    fun inject(app: CatsApplication)

    fun injectSet(settingsActivity: SettingsActivity)

    fun injectAct(activity: MainActivity)

//    fun inject(viewModel: SharedViewModel)
}
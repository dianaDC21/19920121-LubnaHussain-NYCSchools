package com.example.nycschool.di


import com.example.nycschool.ui.MainActivity
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    MainActivityModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}
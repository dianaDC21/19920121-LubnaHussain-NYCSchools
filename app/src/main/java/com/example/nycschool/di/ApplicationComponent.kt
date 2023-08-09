package com.example.nycschool.di

import com.example.nycschool.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SchoolAPIModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}
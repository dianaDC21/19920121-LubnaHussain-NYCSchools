package com.example.nycschool

import android.app.Application
import com.example.nycschool.di.ApplicationComponent
import com.example.nycschool.di.DaggerApplicationComponent

class NYCSchoolApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}
package com.example.nycschool

import android.app.Application
import com.example.nycschool.di.component.ApplicationComponent
import com.example.nycschool.di.component.DaggerApplicationComponent

class NYCSchoolApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}
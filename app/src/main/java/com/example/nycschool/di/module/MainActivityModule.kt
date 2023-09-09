package com.example.nycschool.di.module

import com.example.nycschool.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}
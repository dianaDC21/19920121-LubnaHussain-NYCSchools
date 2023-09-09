package com.example.nycschool.di.module

import com.example.nycschool.ui.fragment.NYCSchoolDetail
import com.example.nycschool.ui.fragment.NYCSchoolFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeNYCSchoolFragment(): NYCSchoolFragment

    @ContributesAndroidInjector
    abstract fun contributeNYCSchoolDetailFragment(): NYCSchoolDetail
}
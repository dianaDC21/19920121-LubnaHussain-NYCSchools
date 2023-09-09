package com.example.nycschool.di

import com.example.nycschool.api.NYCSchoolApi
import com.example.nycschool.repository.NYCSchoolRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(api: NYCSchoolApi): NYCSchoolRepository =
        NYCSchoolRepository(api)
}
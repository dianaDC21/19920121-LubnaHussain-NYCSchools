package com.example.nycschool.di

import dagger.Module


@Module(
    includes = [
            SchoolAPIModule::class,
            ViewModelModule::class,
            RepositoryModule::class
    ]
)
class AppModule
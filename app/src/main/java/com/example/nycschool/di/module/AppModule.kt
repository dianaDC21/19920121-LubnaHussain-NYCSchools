package com.example.nycschool.di.module

import dagger.Module


@Module(
    includes = [
            SchoolAPIModule::class,
            ViewModelModule::class,
            RepositoryModule::class
    ]
)
class AppModule
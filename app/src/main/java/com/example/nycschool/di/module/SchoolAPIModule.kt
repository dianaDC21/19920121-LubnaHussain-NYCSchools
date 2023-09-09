package com.example.nycschool.di.module

import com.example.nycschool.api.NYCSchoolApi
import com.example.nycschool.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class SchoolAPIModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesNYCSchoolAPI(retrofit: Retrofit): NYCSchoolApi {
        return retrofit.create(NYCSchoolApi::class.java)
    }
}
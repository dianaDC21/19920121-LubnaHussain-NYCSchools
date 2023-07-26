package com.example.nycschool.ui

import NYCSchoolViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.nycschool.R
import com.example.nycschool.api.RetrofitInstance
import com.example.nycschool.repository.NYCSchoolRepository
import com.example.nycschool.viewmodel.NYCSchoolViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NYCSchoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val schoolRepository = NYCSchoolRepository(RetrofitInstance.api)
        val viewModelProviderFactory = NYCSchoolViewModelFactory(schoolRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[NYCSchoolViewModel::class.java]
    }
}
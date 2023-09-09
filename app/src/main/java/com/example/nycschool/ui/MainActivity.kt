package com.example.nycschool.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.nycschool.NYCSchoolApplication
import com.example.nycschool.R
import com.example.nycschool.viewmodel.NYCSchoolViewModel
import com.example.nycschool.viewmodel.NYCSchoolViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NYCSchoolViewModel

    @Inject
    lateinit var viewModelFactory: NYCSchoolViewModelFactory //field injection used, dagger will initialise viewModelFactory, need to tell dagger to initialize @Inject ..this so use component in onCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        //component check which fields in activity have Inject, will initialize them
        (application as NYCSchoolApplication).applicationComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NYCSchoolViewModel::class.java]

        /*val schoolRepository = NYCSchoolRepository(RetrofitInstance.api)
        val viewModelProviderFactory = NYCSchoolViewModelFactory(schoolRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[NYCSchoolViewModel::class.java]*/

    }
}
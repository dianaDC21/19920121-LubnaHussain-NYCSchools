package com.example.nycschool.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nycschool.repository.NYCSchoolRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This view model factory class is used to create ViewModel instance, ViewModel requires
 * repository in constructor
 */
@Singleton
class NYCSchoolViewModelFactory @Inject constructor(
    private val schoolRepository: NYCSchoolRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NYCSchoolViewModel(schoolRepository) as T
    }
}
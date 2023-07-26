package com.example.nycschool.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.repository.NYCSchoolRepository
import com.example.nycschool.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This view model class is used to call the repository methods, live data is observed in the fragments
 */
class NYCSchoolViewModel(
    private val nycSchoolRepository: NYCSchoolRepository
) : ViewModel() {
    private val schoolLiveData = MutableLiveData<ApiResult<List<NYCSchoolResponseItem>>>()
    val schoolData: LiveData<ApiResult<List<NYCSchoolResponseItem>>>
        get() = schoolLiveData

    private val satLiveData = MutableLiveData<ApiResult<List<SatScoreResponseItem>>>()
    val satScoreData: LiveData<ApiResult<List<SatScoreResponseItem>>>
        get() = satLiveData

    /**
     * View model method to call GET NYC school api via repository, result is posted via liveData
     */
    fun getNYCSchools() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = nycSchoolRepository.getNYCSchools()
            schoolLiveData.postValue(response)
        }
    }

    /**
     * View model method to call GET sat score api, result is posted via liveData
     */
    fun getSATScore(dbn: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = nycSchoolRepository.getSATScore(dbn)
            satLiveData.postValue(response)
        }
    }

}
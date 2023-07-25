package com.example.nycschool.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.repository.NYCSchoolRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NYCSchoolViewModel(
    private val nycSchoolRepository: NYCSchoolRepository
): ViewModel() {

    val schoolData: LiveData<List<NYCSchoolResponseItem>>
    get() = nycSchoolRepository.schoolData

    private val satLiveData = MutableLiveData<List<SatScoreResponseItem>>()
    val satScoreData: LiveData<List<SatScoreResponseItem>>
    get() = satLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            nycSchoolRepository.getNYCSchools()
        }
    }

    fun getSATScore(dbn: String) : LiveData<List<SatScoreResponseItem>> {
        val satScoreData = MutableLiveData<List<SatScoreResponseItem>>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = nycSchoolRepository.getSATScore(dbn)
            satScoreData.postValue(response)
        }
        return satScoreData
    }
}
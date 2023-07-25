package com.example.nycschool.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nycschool.api.NYCSchoolApi
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.model.SatScoreResponseItem

class NYCSchoolRepository(private val nycSchoolApi: NYCSchoolApi){ //add api instance in const. and call getter
    private val schoolLiveData = MutableLiveData<List<NYCSchoolResponseItem>>()
    val schoolData: LiveData<List<NYCSchoolResponseItem>>
    get() = schoolLiveData

    /*private val satScoreLiveData = MutableLiveData<List<SatScoreResponseItem>>()
    val satScoreData: LiveData<List<SatScoreResponseItem>>
        get() = satScoreLiveData*/

    suspend fun getNYCSchools() {
        val response = nycSchoolApi.getNYCSchool()
        if (response.body() != null) {
            schoolLiveData.postValue(response.body())
        }
    }

    suspend fun getSATScore(dbn: String) : List<SatScoreResponseItem> {
        val response = nycSchoolApi.getSATScore(dbn)
        Log.d("lubna", "repo getSATScore $dbn")
        return if (response.body() != null && response.isSuccessful) {
            Log.d("lubna", "repo sat score ${response.body()!![0].school_name}," +
                    response.body()!![0].sat_math_avg_score
            )
            response.body()!!

        } else emptyList()
    }
}
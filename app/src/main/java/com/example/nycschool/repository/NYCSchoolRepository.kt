package com.example.nycschool.repository

import com.example.nycschool.api.NYCSchoolApi
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.utils.ApiResult
import com.example.nycschool.utils.ApiStatus
import javax.inject.Inject

/**
 * This repository class is used to call the school API endpoints
 */
class NYCSchoolRepository @Inject constructor (private val nycSchoolApi: NYCSchoolApi) {

    /**
     * Repository method to call GET NYC school api, response status is captured
     */
    suspend fun getNYCSchools(): ApiResult<List<NYCSchoolResponseItem>> {
        return try {
            val response = nycSchoolApi.getNYCSchool()
            if (response.isSuccessful && response.body() != null) {
                ApiResult(ApiStatus.SUCCESS, response.body())
            } else {
                ApiResult(ApiStatus.FAILURE)
            }
        } catch (e: Exception) {
            ApiResult(ApiStatus.EXCEPTION)
        }
    }

    /**
     * Repository method to call GET sat score api, response status is captured
     */
    suspend fun getSATScore(dbn: String): ApiResult<List<SatScoreResponseItem>> {
        return try {
            val response = nycSchoolApi.getSATScore(dbn)
            if (response.isSuccessful && response.body() != null) {
                ApiResult(ApiStatus.SUCCESS, response.body())
            } else {
                ApiResult(ApiStatus.FAILURE)
            }
        } catch (e: Exception) {
            ApiResult(ApiStatus.EXCEPTION)
        }
    }
}
package com.example.nycschool.api

import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.utils.Constants.Companion.DB_REFERENCE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NYCSchoolApi {
    @Headers("Content-Type: application/json")
    @GET("resource/s3k6-pzi2")
    suspend fun getNYCSchool(
    ) : Response<List<NYCSchoolResponseItem>>


    @Headers("Content-Type: application/json")
    @GET("resource/f9bf-2cp4.json")
    suspend fun getSATScore(
        @Query(DB_REFERENCE)dbn : String
    ) : Response<List<SatScoreResponseItem>>
}
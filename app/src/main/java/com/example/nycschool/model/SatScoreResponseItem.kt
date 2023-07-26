package com.example.nycschool.model

import com.google.gson.annotations.SerializedName

/**
 * This data class is used to create response body for sat score list api call
 */
data class SatScoreResponseItem(
    @SerializedName("dbn") val dbn: String,
    @SerializedName("num_of_sat_test_takers") val num_of_sat_test_takers: String,
    @SerializedName("sat_critical_reading_avg_score") val sat_critical_reading_avg_score: String,
    @SerializedName("sat_math_avg_score") val sat_math_avg_score: String,
    @SerializedName("sat_writing_avg_score") val sat_writing_avg_score: String,
    @SerializedName("school_name") val school_name: String
)
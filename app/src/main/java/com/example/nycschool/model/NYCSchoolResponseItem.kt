package com.example.nycschool.model

import com.google.gson.annotations.SerializedName

/**
 * This data class is used to create response body for school list api call
 */
data class NYCSchoolResponseItem(
    @SerializedName("dbn") val dbn: String,
    @SerializedName("school_name") val school_name: String,
    @SerializedName("primary_address_line_1") val primary_address_line_1: String,
    @SerializedName("city") val city: String,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("school_email") val school_email: String,
    @SerializedName("website") val website: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)
package com.example.nycschool.utils

/**
 * This class stores the result of API response, can be used across components
 */
class ApiResult<T>(val status: ApiStatus, val apiResponse: T? = null) {
    fun isSuccess() = status == ApiStatus.SUCCESS && apiResponse != null
}
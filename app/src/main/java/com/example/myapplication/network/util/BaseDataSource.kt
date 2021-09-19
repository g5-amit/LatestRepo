package com.example.myapplication.network.util

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response

/**
 * Wrapper to get different state like success, offline and error which returns data over Resource Object
 */
abstract class BaseDataSource(@ApplicationContext val appContext: Context) {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            if(!(NetworkConnectivityUtil.hasNetworkAvailable(appContext))){
                return Resource.Offline()
            }
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    return Resource.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("remoteDataSource", message)
        return Resource.Error(message)
    }

}
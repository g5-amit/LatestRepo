package com.example.myapplication.network.util

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()
    class Error<T>(val msg: String) : Resource<T>()
    class Loading<T>(): Resource<T>()
    class Offline<T>(): Resource<T>()
}
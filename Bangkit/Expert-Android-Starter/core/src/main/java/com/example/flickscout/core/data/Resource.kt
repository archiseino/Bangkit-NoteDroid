package com.example.flickscout.core.data

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Failure<T>(message: String?, data: T? = null) : Resource<T>(data, message)

}
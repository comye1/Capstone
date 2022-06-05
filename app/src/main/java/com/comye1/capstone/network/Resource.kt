package com.comye1.capstone.network

sealed class Resource<T> {
    class Success<T>(val data: T): Resource<T>()
    class Failure<T>(val message: String): Resource<T>()
    class Loading<T>: Resource<T>()
}
package com.comye1.capstone.network

sealed class Resource<T> {
    class Success<T>(data: T): Resource<T>()
    class Failure<T>(message: String): Resource<T>()
    class Loading<T>: Resource<T>()
}
package com.comye1.capstone.network.models

sealed class Response<T>(
    val success: Boolean,
    val data: T,
    val message: String,
    val error: String
)

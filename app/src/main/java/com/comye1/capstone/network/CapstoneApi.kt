package com.comye1.capstone.network

import com.comye1.capstone.network.models.Response
import com.comye1.capstone.network.models.SignUpBody
import com.comye1.capstone.network.models.SignUpData
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface CapstoneApi {
    @POST
    suspend fun signUpUser(signUpBody: SignUpBody): Response<SignUpData>

    @GET
    suspend fun validUser(
        @HeaderMap header: Map<String, String>,
    ): Response<String>


}
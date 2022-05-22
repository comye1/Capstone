package com.comye1.capstone.network

import com.comye1.capstone.network.models.*
import retrofit2.http.*

interface CapstoneApi {

    @GET("api/user/idcheck")
    suspend fun idCheck(@Body body: IdBody): Response<Boolean>

    @POST("api/user")
    suspend fun signUpUser(@Body signUpBody: SignUpBody): Response<SignUpData>

    @GET("api/user")
    suspend fun validUser(
        @HeaderMap header: Map<String, String>,
    ): Response<String>

    @PATCH("api/user")
    suspend fun changeUserNickname(
        @HeaderMap header: Map<String, String>,
        nickname: String,
    ): Response<String>

//    @GET("api/user/profile")
//    suspend fun

    @POST("api/auth")
    suspend fun signInUser(
        @Body signInBody: SignInBody
    ): Response<String>

    @POST("api/follow/{id}")
    suspend fun followUser(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): Response<FollowData>

    @DELETE("api/follow/{id}")
    suspend fun unfollowUser(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): Response<String>

    @GET("api/follow/following/{id}")
    suspend fun getFollowings(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): Response<FollowData>

    @GET("api/follow/follower/{id}")
    suspend fun getFollowers(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): Response<FollowData>


}
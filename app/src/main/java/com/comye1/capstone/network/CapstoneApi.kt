package com.comye1.capstone.network

import com.comye1.capstone.network.models.*
import retrofit2.http.*

interface CapstoneApi {

    @GET("api/user/idcheck/{id}")
    suspend fun idCheck(@Path("id") id: String): BooleanResponse

    @POST("api/user")
    suspend fun signUpUser(@Body signUpBody: SignUpBody): SignUpResponse

    @GET("api/user")
    suspend fun getUser(
        @HeaderMap header: Map<String, String>,
    ): SignUpResponse

    @GET("api/user/{id}")
    suspend fun getUserById(
        @HeaderMap header: Map<String, String>,
        @Query("id") id: Int,
    ): SignUpResponse

    @PATCH("api/user")
    suspend fun changeUserNickname(
        @HeaderMap header: Map<String, String>,
        nickname: String,
    ): StringResponse

    @GET("api/user/plan/{id}")
    suspend fun getUserPlansById(
        @HeaderMap header: Map<String, String>,
        @Query("id") id: Int,
    ): PlanListResponse

    @POST("api/auth")
    suspend fun signInUser(
        @Body signInBody: SignInBody
    ): SignInResponse

    @POST("api/follow/{id}")
    suspend fun followUser(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): FollowResponse

    @DELETE("api/follow/{id}")
    suspend fun unfollowUser(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): StringResponse

    @GET("api/follow/following/{id}")
    suspend fun getFollowings(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): FollowListResponse

    @GET("api/follow/follower/{id}")
    suspend fun getFollowers(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): FollowListResponse

    @POST("api/goal")
    suspend fun postGoal(
        @HeaderMap header: Map<String, String>,
        @Body body: GoalBody
    ): GoalResponse

    @GET("api/goal")
    suspend fun getUserGoals(
        @HeaderMap header: Map<String, String>,
    ): GoalListResponse

    @PATCH("api/goal")
    suspend fun editGoal(
        @HeaderMap header: Map<String, String>,
        @Body body: EditGoalBody
    ): StringResponse

    @GET("api/goal/{id}")
    suspend fun getGoalById(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
    ): GoalResponse

    @POST("api/plan")
    suspend fun postPlan(
        @HeaderMap header: Map<String, String>,
        @Body body: PlanBody
    ): PlanResponse

    @POST("api/plan")
    suspend fun getUserPlans(
        @HeaderMap header: Map<String, String>,
    ): PlanListResponse

    @PATCH("api/plan")
    suspend fun editPlan(
        @HeaderMap header: Map<String, String>,
        @Body body: EditPlanBody
    ): PlanIntListResponse

    @GET("api/plan/{goal_id}/{plan_id}")
    suspend fun getPlanById(
        @HeaderMap header: Map<String, String>,
        @Path("goal_id") goal_id: Int,
        @Path("plan_id") plan_id: Int,
    ): GoalPlanResponse

    @PATCH("api/{goal_id}/{plan_id}/{isChecked}")
    suspend fun completePlan(
        @HeaderMap header: Map<String, String>,
        @Path("goal_id") goal_id: Int,
        @Path("plan_id") plan_id: Int,
        @Path("isChecked") checked: Boolean = false
    ): PlanIntListResponse

    @POST("api/liked/{goal_id}/{plan_id}")
    suspend fun likePlan(
        @HeaderMap header: Map<String, String>,
        @Path("goal_id") goal_id: Int,
        @Path("plan_id") plan_id: Int,
    ): LikeResponse

    @GET("api/liked/{goal_id}/{plan_id}")
    suspend fun getLikes(
        @HeaderMap header: Map<String, String>,
        @Path("goal_id") goal_id: Int,
        @Path("plan_id") plan_id: Int,
    ): LikeListResponse

    @GET("api/feed")
    suspend fun getFeed(
        @HeaderMap header: Map<String, String>,
    ): FeedListResponse
}
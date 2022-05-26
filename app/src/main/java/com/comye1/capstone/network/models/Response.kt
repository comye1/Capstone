package com.comye1.capstone.network.models

data class BooleanResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: Boolean
)

data class SignInResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val token: String = "",
    val data: SignUpData
)


data class StringResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: String
)


data class SignUpResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: SignUpData
)

data class SignUpData(
    val id: Int = -111111,
    val user_id: String = "",
    val password: String = "",
    val nickname: String = "",
)

data class FollowResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: FollowData
)

data class FollowListResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: List<FollowData> = listOf()
)

data class FollowData(
    val id: Int = -111111,
    val follower: Int = -111111,
    val following: Int = -111111,
    val updatedAt: String = "",
    val createdAt: String = "",
    val User: SignUpData = SignUpData(),
)

data class GoalResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: GoalData
)

data class GoalListResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: List<GoalData> = listOf()
)


data class GoalData(
    val copied_from: Int = -111111, // -1이면 원본
    val id: Int = -111111,
    val user_id: String = "",
    val goal_title: String = "",
    val board_category: String = "",
    val Plan: PlanData = PlanData()
)

data class PlanResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: PlanData
)

data class PlanListResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: List<PlanData>
)

data class PlanData(
    val id: Int = -1111111,
    val goal_id: Int = -111111,
    val plan_title: String = "",
    val is_checked: Boolean = false,
    val content: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
)

data class LikeResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: LikeData
)

data class LikeListResponse(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: List<LikeData> = listOf()
)

data class LikeData(
    val id: Int = -111111,
    val plan_id: Int = -11111,
    val createdAt: String = "",
    val updatedAt: String = ""
)

data class PlanIntListRespone(
    val success: Boolean,
    val message: String = "",
    val error: String = "",
    val data: List<Int> = listOf()
)
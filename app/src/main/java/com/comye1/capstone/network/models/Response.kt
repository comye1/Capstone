package com.comye1.capstone.network.models


sealed class Response<T>(
    val success: Boolean,
    val data: T,
    val message: String,
    val error: String
)

data class SignUpData(
    val id: Int,
    val user_id: String,
    val password: String,
    val nickname: String,
)

data class FollowData(
    val id: Int,
    val follower: Int,
    val following: Int,
    val updatedAt: String,
    val createdAt: String,
    val User: SignUpData,
)

data class GoalData(
    val copied_from: Int, // -1이면 원본
    val id: Int,
    val user_id: String,
    val goal_title: String,
    val board_category: String,
    val Plan: PlanData
)

data class PlanData(
    val id: Int,
    val goal_id: Int,
    val plan_title: String,
    val is_checked: Boolean,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)


package com.comye1.capstone.network.models

data class GoalPlanResponse (
    val success: Boolean,
    val Goal: GoalData,
    val Plan: PlanData,
    val message: String,
    val error: String
)
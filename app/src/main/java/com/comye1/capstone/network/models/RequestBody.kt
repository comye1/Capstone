package com.comye1.capstone.network.models

data class RequestBody<T>(val data: T)

data class IdBody(val user_id: String)

data class GoalBody(val goal_title: String, val board_category: String)

data class EditGoalBody(val id: Int, val goal_title: String, val board_category: String)

data class PlanBody(val goal_id: Int, val plan_title: String, val content: String)

data class EditPlanBody(val id: Int, val goal_id: Int, val plan_title: String, val content: String)
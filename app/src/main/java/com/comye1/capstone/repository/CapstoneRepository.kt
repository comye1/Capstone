package com.comye1.capstone.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.comye1.capstone.network.CapstoneApi
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.*
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CapstoneRepository @Inject constructor(
    val api: CapstoneApi,
    val context: Context
) {
    private val categorySharedPref: SharedPreferences = context.getSharedPreferences(
        "category",
        Context.MODE_PRIVATE
    )

    fun saveCategory(category: String) {
        categorySharedPref.edit {
            putString("CATEGORY", category)
            commit()
        }
    }

    fun getSavedCategory(): String? = categorySharedPref.getString("CATEGORY", null)


    private val tokenSharedPref: SharedPreferences = context.getSharedPreferences(
        "token",
        Context.MODE_PRIVATE
    )

    private fun saveToken(token: String) {
        tokenSharedPref.edit {
            putString("TOKEN", token)
            commit()
        }
    }


    private fun getSavedToken(): String? = tokenSharedPref.getString("TOKEN", null)

    private fun String.toTokenMap(): Map<String, String> =
        mapOf(Pair("Authorization", "Bearer $this"))

    lateinit var user: SignUpData

    suspend fun idCheck(id: String): Resource<Boolean> {
        Log.d("repo 1", "called")
        val result = try {
            Log.d("repo 1", "success")
            api.idCheck(id)
        } catch (e: Exception) {
            Log.d("repo 1", "exception ${e.message.toString()}")
            return Resource.Failure(message = e.message.toString())
        }
        Log.d("repo 1", result.success.toString())
        return if (result.success)
            Resource.Success(true)
        else Resource.Failure(message = result.message)
    }

    suspend fun signUpUser(id: String, password: String, nickname: String): Resource<SignUpData> {
        val result = try {
            api.signUpUser(SignUpBody(id, password, nickname))
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success) {
            return Resource.Success(data = result.data)

        } else Resource.Failure(message = result.message)
    }

    suspend fun signInUser(id: String, password: String): Resource<SignUpData> {
        val result = try {
            api.signInUser(signInBody = SignInBody(id, password))
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success) {
            saveToken(result.token)
            user = result.data
            Resource.Success(data = result.data)
        } else Resource.Failure(message = result.message)
    }

    suspend fun getUser(token: String): Boolean {
        Log.d("repo" , "$token getUser")
        val result = try {
            api.getUser(token.toTokenMap())
        } catch (e: Exception) {
            return false
        }
        return if (result.success) {
            user = result.data
            true
        }
        else return false
    }


    suspend fun getUserById(id: Int): SignUpData {
        val token = getSavedToken() ?: ""
//        val result = try {
            return api.getUserById(token.toTokenMap(), id).data
//        } catch (e: Exception) {
//            return Resource.Failure(message = e.message.toString())
//        }
//        return if (result.success)
//            Resource.Success(result.data)
//        else Resource.Failure(message = result.message)
    }


    suspend fun getUserPlansById(id: Int): Resource<List<PlanData>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getUserPlansById(token.toTokenMap(), id)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(result.data)
        else Resource.Failure(message = result.message)
    }


    suspend fun changeUserNickname(newNickname: String): Resource<String> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.changeUserNickname(token.toTokenMap(), newNickname)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun followUser(userId: Int): Resource<FollowData> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.followUser(token.toTokenMap(), userId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun unfollowUser(userId: Int): Resource<String> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.unfollowUser(token.toTokenMap(), userId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getFollowingList(userId: Int): Resource<List<FollowData>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getFollowings(token.toTokenMap(), userId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getFollowerList(userId: Int): Resource<List<FollowData>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getFollowers(token.toTokenMap(), userId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun postGoal(goal: GoalBody): Resource<GoalData> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.postGoal(token.toTokenMap(), goal)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getUserGoals(): Resource<List<GoalData>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getUserGoals(token.toTokenMap())
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun editGoal(goal: EditGoalBody): Resource<String> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.editGoal(token.toTokenMap(), goal)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getGoalById(id: Int): Resource<GoalData> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getGoalById(token.toTokenMap(), id)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun postPlan(plan: PlanBody): Resource<PlanData> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.postPlan(token.toTokenMap(), plan)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getUserPlans(): Resource<List<PlanData>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getUserPlans(token.toTokenMap())
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun editPlan(plan: EditPlanBody): Resource<List<Int>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.editPlan(token.toTokenMap(), plan)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getPlanById(goalId: Int, planId: Int): Resource<GoalPlanResponse> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getPlanById(token.toTokenMap(), goalId, planId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result)
        else Resource.Failure(message = result.message)
    }

    suspend fun completePlan(goalId: Int, planId: Int): Resource<List<Int>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.completePlan(token.toTokenMap(), goal_id = goalId, plan_id = planId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun likePlan(goalId: Int, planId: Int): Resource<LikeData> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.likePlan(token.toTokenMap(), goal_id = goalId, plan_id = planId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getLikes(goalId: Int, planId: Int): Resource<List<LikeData>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getLikes(token.toTokenMap(), goal_id = goalId, plan_id = planId)
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun getFeedList(): Resource<List<FeedData>> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.getFeed(token.toTokenMap())
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)

    }


}
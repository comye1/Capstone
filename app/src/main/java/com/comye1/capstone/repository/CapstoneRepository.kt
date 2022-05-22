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
){
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

    private fun String.toTokenMap(): Map<String, String> = mapOf(Pair("Authorization", "Bearer $this"))

    suspend fun idCheck(id: String): Resource<Boolean> {
        Log.d("repo 1", "called")
        val result = try {
            Log.d("repo 1", "success")
            api.idCheck(IdBody(user_id = id))
        }catch (e:Exception) {
            Log.d("repo 1", "exception ${e.message.toString()}")
            return Resource.Failure(message = e.message.toString())
        }
        Log.d("repo 1", result.success.toString())
        return if (result.success)
            Resource.Success(true)
        else Resource.Failure(message = result.message)
    }

    suspend fun signUpUser(id:String, password: String, nickname: String): Resource<SignUpData> {
        val result = try {
            api.signUpUser(SignUpBody(id, password, nickname))
        }catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun signInUser(id: String, password: String): Resource<String> {
        val result = try {
            api.signInUser(signInBody = SignInBody(id, password))
        } catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success){
            saveToken(result.data)
            Resource.Success(data = result.data)
        }
        else Resource.Failure(message = result.message)
    }

    suspend fun validUser(): Resource<String> {
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.validUser(token.toTokenMap())
        }catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(result.data)
        else Resource.Failure(message = result.message)
    }

    suspend fun changeUserNickname(newNickname: String): Resource<String>{
        val token = getSavedToken() ?: return Resource.Failure("token does not exist")
        val result = try {
            api.changeUserNickname(token.toTokenMap(), newNickname)
        }catch (e: Exception) {
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
        }catch (e: Exception) {
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
        }catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
        else Resource.Failure(message = result.message)
    }

}
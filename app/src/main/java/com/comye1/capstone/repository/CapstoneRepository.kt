package com.comye1.capstone.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.comye1.capstone.network.CapstoneApi
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.SignUpBody
import com.comye1.capstone.network.models.SignUpData
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

    private fun saveToken(prefix: String, token: String) {
        tokenSharedPref.edit {
            putString("TOKEN", "$prefix $token")
            commit()
        }
    }

    private fun getSavedToken(): String? = tokenSharedPref.getString("TOKEN", null)

    private fun String.toTokenMap(): Map<String, String> = mapOf(Pair("Authorization", this))

    suspend fun signUpUser(signUpBody: SignUpBody): Resource<SignUpData> {
        val result = try {
            api.signUpUser(signUpBody)
        }catch (e: Exception) {
            return Resource.Failure(message = e.message.toString())
        }
        return if (result.success)
            Resource.Success(data = result.data)
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


}
package com.comye1.capstone.screens.userdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.FollowData
import com.comye1.capstone.network.models.LikeData
import com.comye1.capstone.network.models.PlanData
import com.comye1.capstone.network.models.SignUpData
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {

    val thisUser = repository.user

    private val messageChannel = Channel<String>()
    val messageFlow = messageChannel.receiveAsFlow()


    suspend fun getUserInfo(id: Int): SignUpData {
        return repository.getUserById(id)
    }

    suspend fun getFollowerList(id: Int): List<FollowData> {
        repository.getFollowerList(id).run {
            when (this) {
                is Resource.Success -> {
                    return if (this.data.isNullOrEmpty())
                        listOf()
                    else {
                        this.data
                    }
                }
                else -> return listOf()
            }
        }
    }

    suspend fun getFollowingList(id: Int): List<FollowData> {
        repository.getFollowingList(id).run {
            when (this) {
                is Resource.Success -> {
                    if (this.data.isNullOrEmpty())
                        return listOf()
                    else return this.data
                }
                else -> return listOf()
            }
        }
    }

    fun followUser(id: Int) {
        viewModelScope.launch {
            repository.followUser(id).run {
                when (this) {
                    is Resource.Success -> {
                        messageChannel.send("팔로우 성공")
                    }
                    is Resource.Failure -> {
                        messageChannel.send("팔로우 실패")
                    }
                }
            }
        }
    }

    fun unfollowUser(id: Int) {
        viewModelScope.launch {
            repository.unfollowUser(id).run {
                when (this) {
                    is Resource.Success -> {
                        messageChannel.send("언팔로우 성공")
                    }
                    is Resource.Failure -> {
                        messageChannel.send("언팔로우 실패")
                    }
                }
            }
        }
    }

    suspend fun getUserPlans(id: Int): List<PlanData> {
        repository.getUserPlansById(id).run {
            when (this) {
                is Resource.Success -> {
                    if (this.data.isNullOrEmpty())
                        return listOf()
                    else return this.data
                }
                else -> return listOf()
            }
        }
    }

    suspend fun getLikes(goalId: Int, planId: Int): List<LikeData> {
        repository.getLikes(goalId, planId).run {
            when (this) {
                is Resource.Success -> {
                    if (this.data.isNullOrEmpty())
                        return listOf()
                    else return this.data
                }
                else -> return listOf()
            }
        }
    }
}
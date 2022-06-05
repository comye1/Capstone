package com.comye1.capstone.screens.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.FeedData
import com.comye1.capstone.network.models.LikeData
import com.comye1.capstone.network.models.PlanData
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {
    // 게시글 목록 불러오기
    // 좋아요 목록 불러오기?
    // 좋아요하기

    val user = repository.user

    var feedList by mutableStateOf<List<FeedData>>(listOf())

    var feedItemList by mutableStateOf<List<FeedItemDataClass>>(listOf())


    private fun getFeedList() {
        viewModelScope.launch {
            repository.getFeedList().run {
                when (this) {
                    is Resource.Success -> {
                        feedList = this.data
                    }
                }
            }
            feedItemList = feedList.map {
                val userName = repository.getUserById(it.user_id).nickname
                FeedItemDataClass(
                    goal_id = it.goal_id,
                    plan_id = it.plan_id,
                    userName = userName,
                    goal = it.goal_title,
                    title = it.plan_title,
                    description = it.content
                )
            }
            getLikeList()
        }
    }

    private fun getLikeList() {
        viewModelScope.launch {
            feedItemList.forEach {
                val like = repository.getLikes(it.goal_id, it.plan_id)
                if (like is Resource.Success)
                    it.copy(like = like.data)
            }
        }
    }

    fun likePlan(goalId: Int, planId: Int, index: Int){
        viewModelScope.launch {
            repository.likePlan(goalId, planId)
            getLikeList()
        }
    }

    init {
        getFeedList()
    }


}
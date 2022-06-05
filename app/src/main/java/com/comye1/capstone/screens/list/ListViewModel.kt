package com.comye1.capstone.screens.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.GoalData
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {

    var userGoalsState by mutableStateOf<Resource<List<GoalData>>>(Resource.Loading())
    // 사용자 목표 불러오기

    var userGoals by mutableStateOf(listOf<GoalData>())

    fun getUserGoals() {
        viewModelScope.launch {
            userGoalsState = repository.getUserGoals()
            if (userGoalsState is Resource.Success) {
                userGoals = (userGoalsState as Resource.Success<List<GoalData>>).data
            }
        }
    }

    init {
        getUserGoals()
    }
}
package com.comye1.capstone.screens.goaldetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.capstone.R
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.GoalBody
import com.comye1.capstone.network.models.GoalData
import com.comye1.capstone.network.models.PlanBody
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {
    var playList by mutableStateOf(samplePlayList)

    var added by mutableStateOf(false)

    fun addToMyList(goal: GoalData) {
        viewModelScope.launch {

            val res =
                repository.postGoal(GoalBody(goal.goal_title, board_category = goal.board_category))
            if (res is Resource.Success) {
                goal.Plan.forEach {
                    repository.postPlan(
                        PlanBody(
                            goal_id = res.data.id,
                            plan_title = it.plan_title,
                            ""
                        )
                    )
                }
                added = true
            }
        }
    }

    fun deleteFromMyList() {
        added = false
    }

    private val messageChannel = Channel<GoalDetailMessage>()
    val messageFlow = messageChannel.receiveAsFlow()


    fun navigateToMyList() {
        viewModelScope.launch {
            messageChannel.send(GoalDetailMessage.NavigateToMyList("1"))
        }
    }

    suspend fun getGoalById(id: Int) =
        repository.getGoalById(id)


    companion object {
        val samplePlayList = PlayList(
            imgId = R.drawable.toeic,
            title = "토익 900 달성",
            author = "김예원",
            playItems = mutableListOf(
                PlayItem(
                    title = "PART5 2회분 풀기, 놓치고 있던 문법 정리하기",
                    "관계부사 어렵다!"
                ),
                PlayItem(
                    title = "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기",
                    "명사 위주 메모하기"
                ),
                PlayItem(
                    "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기",
                    "어제보다는 더 잘 들리는 것 같다~"
                ),
                PlayItem(
                    "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복",
                    "."
                ),
                PlayItem(
                    "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복",
                    "호주 발음이 어렵다ㅠㅠ"
                ),
                PlayItem(
                    "PART3 2회분 풀기, 필요한 정보 파악 & 메모하기",
                    "문제를 미리 읽어야 할 것 같다"
                ),
                PlayItem(
                    "PART3 2회분 풀기, 필요한 정보 파악 & 메모하기",
                    "두시간 집중했다!"
                )

            ),
        )

    }
}

sealed class GoalDetailMessage {
    class NavigateToMyList(val route: String) : GoalDetailMessage()

}
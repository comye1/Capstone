package com.comye1.capstone.screens.mygoal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.capstone.R
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.EditPlanBody
import com.comye1.capstone.network.models.GoalData
import com.comye1.capstone.network.models.PlanData
import com.comye1.capstone.repository.CapstoneRepository
import com.comye1.capstone.screens.goaldetail.GoalDetailMessage
import com.comye1.capstone.screens.goaldetail.PlayItem
import com.comye1.capstone.screens.goaldetail.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGoalViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {
    var currentGoal by mutableStateOf(GoalData())
    var currentPlan by mutableStateOf(PlanData())

    var seekingGoal by mutableStateOf(GoalData())
    var seekingPlan by mutableStateOf(PlanData())

    var added by mutableStateOf(false)

    fun startPlan(goal: GoalData, index: Int) {
        currentPlan = goal.Plan[index]
        currentGoal = goal
    }

    fun startSeekingPlan() {
        currentPlan = seekingPlan
        currentGoal = seekingGoal
    }

    fun seekPlan(goal: GoalData, index: Int) {
        seekingPlan = goal.Plan[index]
        seekingGoal = goal
    }

    fun deleteFromMyList() {
        added = false
    }

    fun movePlan() {
        currentPlan = try {
            currentGoal.Plan.first {
                !it.is_checked
            }
        }catch (e: Exception) {
            currentGoal = GoalData()
            PlanData()
        }
    }

    private val messageChannel = Channel<GoalDetailMessage>()
    val messageFlow = messageChannel.receiveAsFlow()

    fun completePlan(plan: PlanData, content: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.editPlan(
                EditPlanBody(
                    id = plan.id,
                    goal_id = plan.goal_id,
                    content = content,
                    plan_title = plan.plan_title
                )
            ).run {
                if (this is Resource.Success) {
                    repository.completePlan(plan.goal_id, plan.id).run {
                        if (this is Resource.Success)
                            onSuccess()
                    }
                }
            }
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

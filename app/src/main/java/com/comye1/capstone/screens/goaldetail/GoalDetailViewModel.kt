package com.comye1.capstone.screens.goaldetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.comye1.capstone.R
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val repository: CapstoneRepository
): ViewModel() {
    var playList by mutableStateOf(samplePlayList)

    var added by mutableStateOf(false)

    fun addToMyList() {
        added = true
    }

    fun deleteFromMyList() {
        added = false
    }

    companion object {
        val samplePlayList = PlayList(
            imgId = R.drawable.toeic,
            title = "토익 900 달성",
            author = "김예원",
            playItems = listOf(
                PlayItem(
                    title = "PART5 2회분 풀기, 놓치고 있던 문법 정리하기"
                ),
                PlayItem(
                    title = "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기"
                ),
                PlayItem(
                    "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기"
                ),
                PlayItem(
                    "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복"
                ),
                PlayItem(
                    "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복"
                ),
                PlayItem(
                    "PART3 2회분 풀기, 필요한 정보 파악 & 메모하기"
                ),
                PlayItem(
                    "PART3 2회분 풀기, 필요한 정보 파악 & 메모하기"
                )

            ),
            description = "영어의 기반이 생겼다고 느낄 때 기출문제집 공부를 시작했습니다. " +
                    "풀이법은 다른 사람이 가르쳐주는 것보다 본인에게 맞는 방법을 찾는 것이 " +
                    "중요합니다."
        )

    }
}

package com.comye1.capstone.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.comye1.capstone.R
import com.comye1.capstone.network.Resource
import com.comye1.capstone.repository.CapstoneRepository
import com.comye1.capstone.screens.goaldetail.PlayItem
import com.comye1.capstone.screens.goaldetail.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    repository: CapstoneRepository
) : ViewModel() {

    var playList by mutableStateOf<Resource<PlayList>>(Resource.Success(data = newList))

    var showDropdownMenu by mutableStateOf(false)
    var selectedCategory by mutableStateOf("카테고리를 선택하세요")

    fun setCategory(category: String) {
        selectedCategory = category
        closeDropdownMenu()
    }

    fun openDropdownMenu() {
        showDropdownMenu = true
    }

    fun closeDropdownMenu() {
        showDropdownMenu = false
    }

    var currentTitle by mutableStateOf("사용자 님의 새로운 목표")
    var showTitleEditor by mutableStateOf(false)

    fun openTitleEditor() {
        showTitleEditor = true
    }

    fun closeTitleEditor() {
        showTitleEditor = false
    }

    fun saveTitle(newTitle: String) {
        currentTitle = newTitle
        closeTitleEditor()
    }

    var currentDesc by mutableStateOf("설명을 입력해주세요.")
    var showDescEditor by mutableStateOf(false)

    fun openDescEditor() {
        showDescEditor = true
    }

    fun closeDescEditor() {
        showDescEditor = false
    }

    fun saveDesc(newDesc: String) {
        currentDesc = newDesc
        closeDescEditor()
    }

    var currentPlayItem by mutableStateOf("")
    var currentPlayItemPosition by mutableStateOf(-1)

    var showPlayItemEditor by mutableStateOf(false)

    fun setPlayItemContent(content: String) {
        currentPlayItem = content
    }

    fun openPlayItemEditor(index: Int) {
        if (index == -1) {
            currentPlayItem = ""
            currentPlayItemPosition = -1
            Log.d("open", "index : $index currentText: $currentPlayItem")
        } else {
            currentPlayItem = (playList as Resource.Success).data.playItems[index].title
            currentPlayItemPosition = index + 1
        }
        showPlayItemEditor = true
    }

    fun closePlayItemEditor() {
        showPlayItemEditor = false
    }
//    init {
//        playList = Resource.Success(data = sampleList)
//    }

    fun addPlayItem() {
        (playList as Resource.Success).data.playItems.add(PlayItem(currentPlayItem))
        closePlayItemEditor()
    }

    fun savePlayList() {
        val str = (playList as Resource.Success).data.copy(title = currentTitle).toString()
        Log.d("playList", str)
    }

    companion object {
        val newList = PlayList(
            title = "사용자 님의 새로운 목표",
            author = "사용자",
            description = "설명",
            imgId = R.drawable.books,
            playItems = mutableListOf()
        )

        val sampleList = PlayList(
            title = "달성 중인 목표 샘플",
            author = "윤희서",
            description = "책을 꾸준히 읽자",
            imgId = R.drawable.books,
            playItems = listOf(
                PlayItem("11월 1일 ~ 모비딕 제 12장 간추린 생애"),
                PlayItem("11월 8일 ~ 모비딕 제 35장 돛대 꼭대기"),
                PlayItem("11월 15일 ~ 모비딕 제 49장 하이에나"),
            ) as MutableList<PlayItem>
        )
    }
}

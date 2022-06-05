package com.comye1.capstone.screens.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.capstone.network.Resource
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreSearchViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {
    var word by mutableStateOf("")

    var queryResult by mutableStateOf<Resource<String>>(Resource.Loading())

    fun setSearchWord(word: String) {
        this.word = word
    }

    fun getQueryResult() {
        // 검색 결과 불러오기
        queryResult = Resource.Success("성공입니다^^")
    }

}

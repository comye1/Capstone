package com.comye1.capstone.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val repository: CapstoneRepository
) : ViewModel() {

    val user = repository.user

    var selectedCategory by mutableStateOf(repository.getSavedCategory() ?: "")

    fun saveCategory(category: String) {
        selectedCategory = category
        repository.saveCategory(category = category)
    }
}
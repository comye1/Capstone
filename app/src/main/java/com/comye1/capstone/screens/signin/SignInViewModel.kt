package com.comye1.capstone.screens.signin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.capstone.network.Resource
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {

    private val messageChannel = Channel<String>()
    val messageFlow = messageChannel.receiveAsFlow()

    var id by mutableStateOf("")
    var password by mutableStateOf("")


    // 로그인 요청
    fun signIn(onComplete: () -> Unit) {
            viewModelScope.launch {
                repository.signInUser(id, password).also {
                    when (it) {
                        is Resource.Success -> {
                            onComplete()
                        }
                        is Resource.Failure -> {
                            messageChannel.send("로그인 실패")
                        }
                    }
                }
            }

    }
}
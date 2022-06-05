package com.comye1.capstone.screens.signup

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
class SignUpViewModel @Inject constructor(
    private val repository: CapstoneRepository
) : ViewModel() {

    var signUpResult by mutableStateOf(false)

    private val messageChannel = Channel<String>()
    val messageFlow = messageChannel.receiveAsFlow()

    var id by mutableStateOf("")
    var userName by mutableStateOf("")
    var password by mutableStateOf("")


    /**
     * 다이얼로그 상태
     */
    var showIdChecker by mutableStateOf(false)

    /**
     * 아이디 중복 확인 여부
     */
    var idCheckState by mutableStateOf(false)

    /**
     * 중복 확인 다이얼로그 열기
     */
    fun openIdChecker() {
        viewModelScope.launch {
            idCheckState = false
            repository.idCheck(id).also {
                when(it) {
                    is Resource.Success -> {
                        idCheckState = true
                    }
                    is Resource.Failure -> {
//                        messageChannel.send(it.message)
                        idCheckState = false
                    }
                }
            }
            showIdChecker = true
        }
    }

    /**
     * 중복 확인 다이얼로그 닫기
     */
    fun closeIdChecker() {
        showIdChecker = false
    }

    fun signUp() {
        Log.d("signUp", "$id $password $userName")
        viewModelScope.launch {
            repository.signUpUser(id, password, userName).also {
                when (it) {
                    is Resource.Success -> {
                        signUpResult = true
                        messageChannel.send("Success")
                        Log.d("signup 2", it.data.nickname)
                    }
                    is Resource.Failure -> {
                        signUpResult = false
                        messageChannel.send("Failed")
                        Log.d("signup 2", it.message)
                    }
                }
            }
        }
    }


}
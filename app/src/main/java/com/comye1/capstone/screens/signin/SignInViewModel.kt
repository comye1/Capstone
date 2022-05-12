package com.comye1.capstone.screens.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.comye1.capstone.repository.CapstoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
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
//        val emailCheck = emailChecker()
//        if (emailCheck.first) {
//            viewModelScope.launch {
//                repository.signIn(email, password).also {
//                    when(it) {
//                        is Resource.Success -> {
//                            messageChannel.send(it.data?.data?.token?: "no token")
//                            Log.d("signup 2", it.data?.message ?: "null")
                            onComplete()
//                        }
//                        is Resource.Error -> {
//                            messageChannel.send("Sign In Failed")
//                            Log.d("signup 2", it.data?.error ?: "null")
//                        }
//                    }
//                }
//            }
//        }
//        else {
//            // emailCheck.second 띄우기
//            viewModelScope.launch {
//                messageChannel.send(emailCheck.second)
//            }
//        }
    }
}
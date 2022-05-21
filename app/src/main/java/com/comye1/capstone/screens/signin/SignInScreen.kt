package com.comye1.capstone.screens.signin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comye1.capstone.ui.theme.CapstoneTheme
import kotlinx.coroutines.flow.collect

@Composable
fun SignInScreen(
    toMain: () -> Unit,
    toSignUp: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    CapstoneTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8f),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(text = "환영합니다", style = MaterialTheme.typography.h4)
                SignInFields(
                    id = viewModel.id,
                    password = viewModel.password,
                    onIdChange = { viewModel.id = it },
                    onPasswordChange = { viewModel.password = it }
                )
                OutlinedButton(
                    onClick = {
                        viewModel.signIn { toMain() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "로그인")
                }
                OutlinedButton(
                    onClick = {
                        toSignUp()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "회원가입")
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.messageFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}


@Composable
fun SignInFields(
    id: String,
    password: String,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = id,
        onValueChange = onIdChange,
        label = { Text(text = "아이디") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = "비밀번호") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(16.dp))
}
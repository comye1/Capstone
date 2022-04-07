package com.comye1.capstone.screens.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignInScreen(
    toMain: () -> Unit,
    toSignUp: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
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
                email = viewModel.email,
                password = viewModel.password,
                onEmailChange = { viewModel.email = it },
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


@Composable
fun SignInFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(text = "이메일") },
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
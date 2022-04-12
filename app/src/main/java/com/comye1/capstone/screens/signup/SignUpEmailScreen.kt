package com.comye1.capstone.screens.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.comye1.capstone.ui.theme.CapstoneTheme


@Composable
fun SignUpEmailScreen(
    viewModel: SignUpViewModel,
    next: () -> Unit
) {
    CapstoneTheme {
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = {
                viewModel.email = it
                viewModel.verificationRequested = false
                viewModel.verificationCode = ""
            },
            label = { Text(text = "이메일") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 이메일 유효성 검사 (공백, 이메일 패턴)
        when {
            viewModel.email.isBlank() -> {
                Text(text = "이메일 주소를 입력해주세요.", color = Color.Red)
                viewModel.showVerifyButton = false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(viewModel.email).matches() -> {
                Text(text = "이메일이 유효하지 않습니다.", color = Color.Red)
                viewModel.showVerifyButton = false
            }
            else -> {
                Text(text = "유효한 이메일입니다.", color = Color.Green)
                viewModel.showVerifyButton = true
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // request
                viewModel.requestEmailVerificationCode()
            },
            enabled = !viewModel.verificationRequested && viewModel.showVerifyButton
        ) {
            Text(text = "인증 메일 보내기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.verificationRequested) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.verificationCode,
                    onValueChange = {
                        viewModel.verificationCode = it
                    },
                    label = { Text(text = "인증 코드") },
                    modifier = Modifier.fillMaxWidth(.7f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.verifyEmailCode()
                    },
                    enabled = !viewModel.verificationResult
                ) {
                    Text(text = "확인하기")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                next()
            },
            enabled = viewModel.verificationResult
        ) {
            Text(text = "다음")
        }
    }
}


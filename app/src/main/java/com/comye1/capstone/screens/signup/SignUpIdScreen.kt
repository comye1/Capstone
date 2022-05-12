package com.comye1.capstone.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.comye1.capstone.ui.theme.CapstoneTheme


@Composable
fun SignUpIdScreen(
    viewModel: SignUpViewModel,
    next: () -> Unit
) {
    CapstoneTheme {
        OutlinedTextField(
            value = viewModel.id,
            onValueChange = {
                if (it.length < 21) {
                    viewModel.id = it
                    viewModel.idCheckState = false
                }
            },
            label = { Text(text = "아이디 (4~20자)") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // request
                viewModel.openIdChecker()
            },
            enabled = viewModel.id.length in 4..20 && !viewModel.idCheckState
        ) {
            Text(text = if (viewModel.idCheckState) "아이디 중복 확인 완료" else "아이디 중복 확인")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.showIdChecker) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                Column(
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = if (viewModel.idCheckState) "사용 가능한 아이디입니다." else "이미 존재하는 아이디입니다.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.closeIdChecker()
                        },
                    ) {
                        Text(text = "확인")
                    }
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                next()
            },
            enabled = viewModel.idCheckState
        ) {
            Text(text = "다음")
        }
    }
}


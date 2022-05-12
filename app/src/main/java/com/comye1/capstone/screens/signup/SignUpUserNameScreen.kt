package com.comye1.capstone.screens.signup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SignUpUserNameScreen(
    userName: String,
    setUserName: (String) -> Unit,
    next: () -> Unit
) {
    OutlinedTextField(
        value = userName,
        onValueChange = {
            setUserName(it)
        },
        label = { Text(text = "사용자 이름 (2~10자)") },
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(16.dp))


    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            next()
        },
        enabled = userName.length in 2..10
    ) {
        Text(text = "다음")
    }

}
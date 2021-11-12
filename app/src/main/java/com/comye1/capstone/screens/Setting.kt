package com.comye1.capstone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.comye1.capstone.R

@Composable
fun SettingScreen() {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Settings") },
        )
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ProfileSection(userName = "예원", logInStatus = true, logIn = { /*TODO*/ }, logOut = {})
            Spacer(modifier = Modifier.height(32.dp))
            PreferenceSection()
        }
    }
}

@Composable
fun ProfileSection(
    profileImage: Painter = painterResource(id = R.drawable.sample),
    userName: String,
    logInStatus: Boolean,
    logIn: () -> Unit = {},
    logOut: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Bottom)
    ) {
        Column(
            Modifier
                .size(128.dp)
        ) {
            Image(
                painter = profileImage,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentDescription = "profile image"
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.height(128.dp)
        ) {
            Text(text = userName, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                TextButton(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "정보 수정")
                }
                Spacer(modifier = Modifier.width(16.dp))
                if (logInStatus) {
                    TextButton(
                        onClick = logOut,
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "로그아웃")
                    }
                } else {
                    TextButton(
                        onClick = logIn,
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "로그인")
                    }
                }
            }
        }
    }
}

@Composable
fun PreferenceSection(

) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        PreferenceItem(text = "계정 설정", onClick = {})
        Divider()
        PreferenceItem(text = "관심 주제 설정", onClick = {})
        Divider()
        PreferenceItem(text = "알림 설정", onClick = {})
    }
}

@Composable
fun PreferenceItem(text: String, onClick: () -> Unit) {
    Text(text = text, modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(16.dp))
}
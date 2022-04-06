package com.comye1.capstone.screens.userdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserDetailScreen(toBack: () -> Unit, toGoal: () -> Unit) {
    /*
     앱바 등을 어떻게 할지가 고민이다.

     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("{사용자 이름}") },
                navigationIcon = {
                    IconButton(onClick = { toBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "navigate back"
                        )
                    }
                }
            )
        }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("User Detail")

            TextButton(onClick = { toGoal() }) {
                Text(text = "goal_detail")
            }

        }
    }
}
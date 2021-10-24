package com.comye1.capstone.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun SettingScreen() {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Settings") },
        )
    }) {

    }
}
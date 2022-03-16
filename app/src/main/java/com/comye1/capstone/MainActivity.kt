package com.comye1.capstone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.comye1.capstone.navigation.BottomNavigationBar
import com.comye1.capstone.navigation.Navigator
import com.comye1.capstone.ui.theme.CapstoneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            CapstoneTheme {
                Scaffold(bottomBar = {
                    BottomNavigationBar(navController = navController)
                }) {
                    Navigator(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


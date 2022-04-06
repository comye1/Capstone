package com.comye1.capstone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comye1.capstone.navigation.BottomNavigationBar
import com.comye1.capstone.navigation.Screen
import com.comye1.capstone.screens.GoalDetailScreen
import com.comye1.capstone.screens.explore.Explore
import com.comye1.capstone.screens.feed.FeedScreen
import com.comye1.capstone.screens.list.ListScreen
import com.comye1.capstone.screens.mygoal.MyGoalScreen
import com.comye1.capstone.screens.userdetail.UserDetailScreen
import com.comye1.capstone.screens.settings.SettingsScreen
import com.comye1.capstone.ui.theme.CapstoneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            val (bottomBarShown, showBottomBar) = remember {
                mutableStateOf(true)
            }

            CapstoneTheme {
                Scaffold(bottomBar = {
                    if(bottomBarShown) {
                        BottomNavigationBar(navController = navController)
                    }
                }) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Feed.route
                    ) {
                        composable(Screen.Feed.route) {
                            showBottomBar(true)
                            FeedScreen(paddingValues = paddingValues)
                        }
                        composable(Screen.Explore.route) {
                            showBottomBar(true)
                            Explore(paddingValues)
                        }
                        composable(Screen.List.route) {
                            showBottomBar(true)
                            ListScreen(paddingValues)
                        }
                        composable(Screen.Setting.route) {
                            showBottomBar(true)
                            SettingsScreen(paddingValues)
                        }
                        composable("user_detail"){
                            showBottomBar(false)
                            UserDetailScreen(toBack = {  }) {

                            }
                        }
                        composable("goal_Detail") {
                            showBottomBar(false)
                            GoalDetailScreen {

                            }
                        }
                        composable("my_goal") {
                            showBottomBar(false)
                            MyGoalScreen()
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


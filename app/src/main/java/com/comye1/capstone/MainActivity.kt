package com.comye1.capstone

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comye1.capstone.navigation.BottomNavigationBar
import com.comye1.capstone.navigation.Screen
import com.comye1.capstone.screens.GoalDetailScreen
import com.comye1.capstone.screens.explore.Explore
import com.comye1.capstone.screens.explore.ExploreSearchScreen
import com.comye1.capstone.screens.feed.FeedScreen
import com.comye1.capstone.screens.list.ListScreen
import com.comye1.capstone.screens.mygoal.MyGoalScreen
import com.comye1.capstone.screens.player.PlayerBottomSheetContent
import com.comye1.capstone.screens.player.PlayerBottomSheetHandle
import com.comye1.capstone.screens.settings.SettingsScreen
import com.comye1.capstone.screens.userdetail.UserDetailScreen
import com.comye1.capstone.ui.ExitDialog
import com.comye1.capstone.ui.theme.CapstoneTheme
import com.comye1.capstone.ui.theme.StatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            window.statusBarColor = StatusBarColor.toArgb()

            Log.d("mainactivity", "oncreate")

            val navController = rememberNavController()

            val (bottomBarShown, showBottomBar) = remember {
                mutableStateOf(true)
            }

            val (exitDialogShown, showExitDialog) = remember {
                mutableStateOf(false)
            }

            CapstoneTheme {
                Scaffold(
                    bottomBar = {
                        // bottom bar visibility 애니메이션
                        AnimatedVisibility(visible = bottomBarShown, enter = expandVertically(), exit = shrinkVertically()) {
                            BottomNavigationBar(navController = navController)
                        }
                    },
                ) { paddingValues ->


                    val scope = rememberCoroutineScope()
                    val scaffoldState = rememberBottomSheetScaffoldState()

                    BottomSheetScaffold(
                        sheetContent = {
                            PlayerBottomSheetHandle {
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                            PlayerBottomSheetContent(paddingValues.calculateBottomPadding())
                        },
                        scaffoldState = scaffoldState,
                        sheetPeekHeight = (96.dp + paddingValues.calculateBottomPadding()),
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Feed.route
                        ) {
                            composable(Screen.Feed.route) {
                                showBottomBar(true)
                                FeedScreen(
                                    toGoalDetail = { navController.navigate("goal_detail") },
                                    toUserDetail = { navController.navigate("user_detail") }
                                )
                            }
                            composable(Screen.Explore.route) {
                                showBottomBar(true)
                                Explore(
                                    paddingValues = paddingValues,
                                    toGoalDetail = { navController.navigate("goal_detail") },
                                    toUserDetail = { navController.navigate("user_detail") },
                                    toSearch = { navController.navigate("search") }
                                )
                            }
                            composable(Screen.List.route) {
                                showBottomBar(true)
                                ListScreen(paddingValues)
                            }
                            composable(Screen.Setting.route) {
                                showBottomBar(true)
                                SettingsScreen(paddingValues)
                            }
                            composable("user_detail") {
                                showBottomBar(false)
                                UserDetailScreen(toBack = { }) {

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
                            composable("search") {
                                showBottomBar(false)
                                ExploreSearchScreen {
                                    navController.popBackStack()
                                }
                            }

                        }
                    }
                }
            }
            /*
            뒤로가기 동작 처리
            */
            BackHandler(
                onBack = {
                    showExitDialog(true)
                }
            )
            /*
            종료 다이얼로그
             */
            if (exitDialogShown) {
                ExitDialog(onDismiss = { showExitDialog(false) }) {
                    finishAffinity()
                }
            }
        }
    }
}

package com.comye1.capstone

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.comye1.capstone.navigation.BottomNavigationBar
import com.comye1.capstone.navigation.Screen
import com.comye1.capstone.screens.create.CreateScreen
import com.comye1.capstone.screens.explore.Explore
import com.comye1.capstone.screens.explore.ExploreSearchScreen
import com.comye1.capstone.screens.feed.FeedScreen
import com.comye1.capstone.screens.goaldetail.GoalDetailScreen
import com.comye1.capstone.screens.list.ListScreen
import com.comye1.capstone.screens.mygoal.MyGoalScreen
import com.comye1.capstone.screens.player.PlayerBottomSheetContent
import com.comye1.capstone.screens.player.PlayerBottomSheetHandle
import com.comye1.capstone.screens.userdetail.UserDetailScreen
import com.comye1.capstone.settings.SettingsScreen
import com.comye1.capstone.ui.ExitDialog
import com.comye1.capstone.ui.theme.CapstoneTheme
import com.comye1.capstone.ui.theme.StatusBarColor
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagerApi
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
                        AnimatedVisibility(
                            visible = bottomBarShown,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
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
                                    toGoalDetail = { navController.navigate("goal_detail/$it") },
                                    toUserDetail = { navController.navigate("user_detail/$it") }
                                )
                            }
                            composable(Screen.Explore.route) {
                                showBottomBar(true)
                                Explore(
                                    paddingValues = paddingValues,
                                    toGoalDetail = { navController.navigate("goal_detail") },
                                    toSearch = { navController.navigate("search") }
                                )
                            }
                            composable(Screen.List.route) {
                                showBottomBar(true)
                                ListScreen(
                                    paddingValues,
                                    toMyGoal = { goalId ->
                                        navController.navigate("my_goal/$goalId")
                                    },
                                    toCreateScreen = { navController.navigate("create") },
                                )
                            }
                            composable("create") {
                                CreateScreen() { navController.popBackStack() }

                            }
                            composable(Screen.Setting.route) {
                                showBottomBar(true)
                                SettingsScreen()
                            }
                            composable(
                                route = "user_detail/{userId}",
                                arguments = listOf(navArgument(name = "userId") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                })
                            ) {
                                val userId = it.arguments?.getInt("userId") ?: 0
                                showBottomBar(false)
                                UserDetailScreen(
                                    userId = userId,
                                    toBack = {
                                        navController.popBackStack()
                                    },
                                    toGoal = { id ->
                                        navController.navigate("goal_detail/$id")
                                    }
                                )
                            }
                            composable(
                                "goal_Detail/{id}", arguments = listOf(navArgument(name = "id") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                })
                            ) {
                                val id = it.arguments!!.getInt("id")
                                showBottomBar(false)
                                GoalDetailScreen(
                                    id,
                                    toBack = {
                                        navController.popBackStack()
                                    },
                                    toMyGoal = { goalId ->
                                        navController.navigate("my_goal/$goalId")
                                    }
                                )
                            }
                            composable(
                                "my_goal/{goalId}",
                                arguments = listOf(navArgument(name = "goalId") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                })
                            ) {
                                val id = it.arguments!!.getInt("goalId")
                                showBottomBar(false)
                                MyGoalScreen(id) {
                                    navController.popBackStack()
                                }
                            }
                            composable("search") {
                                showBottomBar(false)
                                ExploreSearchScreen(
                                    toGoalDetail = { navController.navigate("goal_detail/$it") }
                                ) {
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

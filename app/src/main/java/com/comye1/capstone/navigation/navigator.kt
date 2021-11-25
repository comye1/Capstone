package com.comye1.capstone.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.comye1.capstone.screens.*

sealed class Screen(
    val route: String
) {
    object Feed : Screen("Feed")
    object Explore : Screen("Explore")
    object List : Screen("My List")
    object Setting : Screen("Setting")
    object Create : Screen("Create")
}

@Composable
fun Navigator(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Feed.route
    ) {
        composable(Screen.Feed.route) {
            FeedScreen()
        }
        composable(Screen.Explore.route) {
            ExploreScreen()
        }
        composable(Screen.List.route) {
            // TODO : list screen
            ListScreen(navController)
        }
        composable(Screen.Create.route) {
            CreateScreen(toBack = {navController.popBackStack()})
        }
        composable(Screen.Setting.route) {
            // TODO : setting screen
            SettingScreen()
        }
    }
}


data class BottomNavItem(
    val route: String,
    val name: String,
    val icon: ImageVector
)

object BottomNav {
    val items = listOf(
        BottomNavItem(Screen.Feed.route, "Feed", Icons.Default.Dashboard),
        BottomNavItem(Screen.Explore.route, "Explore", Icons.Default.Explore),
        BottomNavItem(Screen.List.route, "List", Icons.Default.PlaylistPlay),
        BottomNavItem(Screen.Setting.route, "Settings", Icons.Default.Settings),
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        Divider()
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {
            BottomNav.items.forEach { item ->
                BottomNavigationItem(
                    selected = item.route == currentRoute,
                    enabled = item.route != currentRoute,
                    onClick = { navController.navigate(item.route) },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                    label = { Text(item.name) },
                    selectedContentColor = MaterialTheme.colors.primaryVariant,
                    unselectedContentColor = Color.DarkGray,
                    alwaysShowLabel = false
                )
            }
        }
    }

}
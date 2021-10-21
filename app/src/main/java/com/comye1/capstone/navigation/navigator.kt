package com.comye1.capstone.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.comye1.capstone.screens.FeedScreen
import com.comye1.capstone.screens.ListScreen

sealed class Screen(
    val route: String
) {
    object Feed : Screen("Feed")
    object List : Screen("List")
    object Setting : Screen("Setting")
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
        composable(Screen.List.route) {
            // TODO : list screen
            ListScreen()
        }
        composable(Screen.Setting.route) {
            // TODO : setting screen
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
        BottomNavItem(Screen.Feed.route, "Home", Icons.Outlined.Home),
        BottomNavItem(Screen.List.route, "List", Icons.Outlined.List),
        BottomNavItem(Screen.Setting.route, "Settings", Icons.Outlined.Settings),
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 1.dp,
        modifier = Modifier.padding(4.dp)
    ) {
        BottomNav.items.forEach { item ->
            BottomNavigationItem(
                selected = item.route == currentRoute,
                enabled = item.route != currentRoute,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
//                selectedContentColor = DeepOrange,
                unselectedContentColor = Color.DarkGray
            )
        }
    }
}
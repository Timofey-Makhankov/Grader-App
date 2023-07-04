package ch.timofey.grader.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.icons.Calculate
import ch.timofey.grader.ui.components.icons.Help

object NavigationDrawerItems {
    val list = listOf(
        MenuItem(
            id = "home",
            title = "Home",
            contentDescription = "Go to Home Screen",
            icon = Icons.Default.Home,
            onNavigate = Screen.MainScreen.route
        ),
        MenuItem(
            id = "grade_calculator",
            title = "Grade Calculator",
            contentDescription = "Go to Custom Grade Calculator",
            icon = Icons.Calculate,
            onNavigate = Screen.CalculatorScreen.route
        ),
        MenuItem(
            id = "help",
            title = "Help",
            contentDescription = "Go to Help Screen",
            icon = Icons.Help,
            onNavigate = "" // TODO
        ),
        MenuItem(
            id = "share",
            title = "Share",
            contentDescription = "Go to Share Screen",
            icon = Icons.Default.Share,
            onNavigate = Screen.ShareScreen.route
        ),
        MenuItem(
            id = "setting",
            title = "Settings",
            contentDescription = "Go to Settings Screen",
            icon = Icons.Default.Settings,
            onNavigate = Screen.SettingsScreen.route
        )
    )
}
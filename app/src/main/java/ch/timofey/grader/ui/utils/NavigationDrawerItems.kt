package ch.timofey.grader.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import ch.timofey.grader.navigation.Screen

object NavigationDrawerItems{
    fun getNavigationDrawerItems(): List<MenuItem> {
        return listOf(
            MenuItem(
                id = "home",
                title = "Home",
                contentDescription = "Go to Home Screen",
                icon = Icons.Default.Home,
                onNavigate = Screen.MainScreen.route
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
}
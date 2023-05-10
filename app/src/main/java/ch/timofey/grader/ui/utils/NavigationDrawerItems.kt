package ch.timofey.grader.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share

object NavigationDrawerItems{
    fun getNavigationDrawerItems(): List<MenuItem> {
        return listOf(
            MenuItem(
                id = "home",
                title = "Home",
                contentDescription = "Go to Home Screen",
                icon = Icons.Default.Home,
                onNavigate = ""
            ),
            MenuItem(
                id = "share",
                title = "Share",
                contentDescription = "Go to Share Screen",
                icon = Icons.Default.Share,
                onNavigate = ""
            ),
            MenuItem(
                id = "setting",
                title = "Settings",
                contentDescription = "Go to Settings Screen",
                icon = Icons.Default.Settings,
                onNavigate = ""
            )
        )
    }
}
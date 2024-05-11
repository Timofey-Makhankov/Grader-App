package ch.timofey.grader.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import ch.timofey.grader.R
import ch.timofey.grader.type.MenuItem
import ch.timofey.grader.ui.components.atom.icons.Calculate

object NavigationDrawerItems {
    val list = listOf(
        MenuItem(
            id = "home",
            title = R.string.home.toString(),
            contentDescription = R.string.go_to_home_screen.toString(),
            icon = Icons.Default.Home,
            onNavigate = Screen.MainScreen.route
        ), MenuItem(
            id = "grade_calculator",
            title = R.string.grade_calculator.toString(),
            contentDescription = R.string.go_to_custom_grade_calc.toString(),
            icon = Icons.Calculate,
            onNavigate = Screen.CalculatorScreen.route
        ), MenuItem(
            id = "about",
            title = R.string.about.toString(),
            contentDescription = R.string.go_to_share_screen.toString(),
            icon = Icons.Default.Info,
            onNavigate = Screen.ShareScreen.route
        ), MenuItem(
            id = "setting",
            title = R.string.settings.toString(),
            contentDescription = R.string.go_to_settings_screen.toString(),
            icon = Icons.Default.Settings,
            onNavigate = Screen.SettingsScreen.route
        )
    )
}
package ch.timofey.grader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.timofey.grader.ui.screen.MainScreen
import ch.timofey.grader.ui.screen.SecondScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.SecondScreen.route) {
            SecondScreen(navController = navController)
        }
    }
}
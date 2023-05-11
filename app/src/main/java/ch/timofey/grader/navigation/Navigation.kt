package ch.timofey.grader.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.timofey.grader.ui.screen.CreateSchoolScreen
import ch.timofey.grader.ui.screen.MainScreen
import ch.timofey.grader.ui.screen.SecondScreen
import ch.timofey.grader.ui.vm.CreateSchoolViewModel
import ch.timofey.grader.ui.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackBarHostState = remember { SnackbarHostState() }
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            val state by viewModel.uiState.collectAsState()
            MainScreen(
                drawerState = drawerState,
                onEvent = viewModel::onEvent,
                state = state,
                uiEvent = viewModel.uiEvent,
                onNavigate = {
                    navController.navigate(it.route)
                },
                snackBarHostState = snackBarHostState
            )
        }
        composable(route = Screen.SecondScreen.route) {
            SecondScreen(navController = navController)
        }
        composable(route = Screen.CreateSchoolScreen.route) {
            val viewModel = hiltViewModel<CreateSchoolViewModel>()
            val state by viewModel.uiState.collectAsState()
            CreateSchoolScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { navController.popBackStack() },
                snackBarHostState = snackBarHostState
            )
        }
    }
}
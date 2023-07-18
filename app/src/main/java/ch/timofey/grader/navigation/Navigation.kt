package ch.timofey.grader.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ch.timofey.grader.ui.screen.calculator.CalculatorScreen
import ch.timofey.grader.ui.screen.calculator.CalculatorViewModel
import ch.timofey.grader.ui.screen.create_division.CreateDivisionScreen
import ch.timofey.grader.ui.screen.create_division.CreateDivisionViewModel
import ch.timofey.grader.ui.screen.create_exam.CreateExamScreen
import ch.timofey.grader.ui.screen.create_exam.CreateExamViewModel
import ch.timofey.grader.ui.screen.create_module.CreateModuleScreen
import ch.timofey.grader.ui.screen.create_module.CreateModuleViewModel
import ch.timofey.grader.ui.screen.create_school.CreateSchoolScreen
import ch.timofey.grader.ui.screen.settings.SettingsScreen
import ch.timofey.grader.ui.screen.create_school.CreateSchoolViewModel
import ch.timofey.grader.ui.screen.division_list.DivisionListScreen
import ch.timofey.grader.ui.screen.division_list.DivisionListViewModel
import ch.timofey.grader.ui.screen.exam_list.ExamListScreen
import ch.timofey.grader.ui.screen.exam_list.ExamListViewModel
import ch.timofey.grader.ui.screen.module_list.ModuleListScreen
import ch.timofey.grader.ui.screen.module_list.ModuleListViewModel
import ch.timofey.grader.ui.screen.school_list.SchoolListScreen
import ch.timofey.grader.ui.screen.school_list.SchoolListViewModel
import ch.timofey.grader.ui.screen.settings.SettingsViewModel
import ch.timofey.grader.ui.screen.share.AboutScreen
import ch.timofey.grader.ui.screen.share.AboutViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackBarHostState = remember { SnackbarHostState() }
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            val viewModel = hiltViewModel<SchoolListViewModel>()
            val state by viewModel.uiState.collectAsState()
            SchoolListScreen(
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
        composable(route = Screen.SettingsScreen.route) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            val state by viewModel.uiState.collectAsState()
            SettingsScreen(drawerState = drawerState,
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onNavigate = {
                    navController.navigate(it.route)
                })
        }
        composable(route = Screen.ShareScreen.route) {
            val viewModel = hiltViewModel<AboutViewModel>()
            AboutScreen(drawerState = drawerState, uiEvent = viewModel.uiEvent, onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(route = Screen.CalculatorScreen.route) {
            val viewModel = hiltViewModel<CalculatorViewModel>()
            val state by viewModel.uiState.collectAsState()
            CalculatorScreen(
                state = state, onEvent = viewModel::onEvent, onNavigate = {
                    navController.navigate(it.route)
                }, drawerState = drawerState
            )
        }
        composable(
            route = Screen.DivisionScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<DivisionListViewModel>()
            val state by viewModel.uiState.collectAsState()
            DivisionListScreen(state = state,
                onEvent = viewModel::onEvent,
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                drawerState = drawerState,
                uiEvent = viewModel.uiEvent,
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.CreateDivisionScreen.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<CreateDivisionViewModel>()
            val state by viewModel.uiState.collectAsState()
            CreateDivisionScreen(state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = {
                    navController.popBackStack()
                })
        }
        composable(
            route = Screen.ModuleScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<ModuleListViewModel>()
            val state by viewModel.uiState.collectAsState()
            ModuleListScreen(state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onNavigate = {
                    navController.navigate(it.route)
                },
                drawerState = drawerState,
                onPopBackStack = {
                    navController.popBackStack()
                },
                snackBarHostState = snackBarHostState)
        }
        composable(
            route = Screen.CreateModuleScreen.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<CreateModuleViewModel>()
            val state by viewModel.uiState.collectAsState()
            CreateModuleScreen(state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = {
                    navController.popBackStack()
                })
        }
        composable(
            route = Screen.ExamScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<ExamListViewModel>()
            val state by viewModel.uiState.collectAsState()
            ExamListScreen(state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                drawerState = drawerState,
                onPopBackStack = { navController.popBackStack() },
                onNavigate = { navController.navigate(it.route) })
        }
        composable(
            route = Screen.CreateExamScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<CreateExamViewModel>()
            val state by viewModel.uiState.collectAsState()
            CreateExamScreen(state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { navController.popBackStack() })
        }
    }
}
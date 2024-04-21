package ch.timofey.grader.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ch.timofey.grader.ui.screen.about.AboutScreen
import ch.timofey.grader.ui.screen.about.AboutViewModel
import ch.timofey.grader.ui.screen.calculator.CalculatorScreen
import ch.timofey.grader.ui.screen.calculator.CalculatorViewModel
import ch.timofey.grader.ui.screen.division.create_division.CreateDivisionScreen
import ch.timofey.grader.ui.screen.division.create_division.CreateDivisionViewModel
import ch.timofey.grader.ui.screen.division.division_list.DivisionListScreen
import ch.timofey.grader.ui.screen.division.division_list.DivisionListViewModel
import ch.timofey.grader.ui.screen.division.update_division.UpdateDivisionScreen
import ch.timofey.grader.ui.screen.division.update_division.UpdateDivisionViewModel
import ch.timofey.grader.ui.screen.exam.create_exam.CreateExamScreen
import ch.timofey.grader.ui.screen.exam.create_exam.CreateExamViewModel
import ch.timofey.grader.ui.screen.exam.exam_list.ExamListScreen
import ch.timofey.grader.ui.screen.exam.exam_list.ExamListViewModel
import ch.timofey.grader.ui.screen.exam.update_exam.UpdateExamScreen
import ch.timofey.grader.ui.screen.exam.update_exam.UpdateExamViewModel
import ch.timofey.grader.ui.screen.module.create_module.CreateModuleScreen
import ch.timofey.grader.ui.screen.module.create_module.CreateModuleViewModel
import ch.timofey.grader.ui.screen.module.module_list.ModuleListScreen
import ch.timofey.grader.ui.screen.module.module_list.ModuleListViewModel
import ch.timofey.grader.ui.screen.module.update_module.UpdateModuleScreen
import ch.timofey.grader.ui.screen.module.update_module.UpdateModuleViewModel
import ch.timofey.grader.ui.screen.school.create_school.CreateSchoolScreen
import ch.timofey.grader.ui.screen.school.create_school.CreateSchoolViewModel
import ch.timofey.grader.ui.screen.school.school_list.SchoolListScreen
import ch.timofey.grader.ui.screen.school.school_list.SchoolListViewModel
import ch.timofey.grader.ui.screen.school.update_school.UpdateSchoolScreen
import ch.timofey.grader.ui.screen.school.update_school.UpdateSchoolViewModel
import ch.timofey.grader.ui.screen.settings.SettingsScreen
import ch.timofey.grader.ui.screen.settings.SettingsViewModel

@Composable
fun Navigation(
    snackBarHostState: SnackbarHostState
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavHost(navController = navController,
        startDestination = Screen.MainScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) }) {
        composable(route = Screen.MainScreen.route) { navBackStackEntry ->
            val viewModel = hiltViewModel<SchoolListViewModel>()
            val state by viewModel.uiState.collectAsState()
            val stackEntry =
                navBackStackEntry.savedStateHandle.get<SnackbarVisuals>("show-snackBar")
            SchoolListScreen(
                drawerState = drawerState,
                onEvent = viewModel::onEvent,
                state = state,
                uiEvent = viewModel.uiEvent,
                onNavigate = { navController.navigate(it.route) },
                snackBarHostState = snackBarHostState,
                stackEntryValue = stackEntry
            )
        }
        composable(route = Screen.CreateSchoolScreen.route) {
            val viewModel = hiltViewModel<CreateSchoolViewModel>()
            val state by viewModel.uiState.collectAsState()
            CreateSchoolScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
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
                snackBarHostState = snackBarHostState,
                onNavigate = {
                    navController.navigate(it.route)
                })
        }
        composable(route = Screen.ShareScreen.route) {
            val viewModel = hiltViewModel<AboutViewModel>()
            AboutScreen(drawerState = drawerState, uiEvent = viewModel.uiEvent, onNavigate = {
                navController.navigate(it.route)
            }, onEvent = viewModel::onEvent)
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
        ) { navBackStackEntry ->
            val stackEntry =
                navBackStackEntry.savedStateHandle.get<SnackbarVisuals>("show-snackBar")
            val viewModel = hiltViewModel<DivisionListViewModel>()
            val state by viewModel.uiState.collectAsState()
            DivisionListScreen(state = state,
                onEvent = viewModel::onEvent,
                onNavigate = {
                    navController.navigate(it.route)
                },
                onPopBackStack = {
                    navController.popBackStackSafe()
                },
                drawerState = drawerState,
                uiEvent = viewModel.uiEvent,
                snackBarHostState = snackBarHostState,
                stackEntryValue = stackEntry
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
            CreateDivisionScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.ModuleScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) { navBackStackEntry ->
            val stackEntry =
                navBackStackEntry.savedStateHandle.get<SnackbarVisuals>("show-snackBar")
            val viewModel = hiltViewModel<ModuleListViewModel>()
            val state by viewModel.uiState.collectAsState()
            ModuleListScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onNavigate = { navController.navigate(it.route) },
                drawerState = drawerState,
                onPopBackStack = { navController.popBackStackSafe() },
                snackBarHostState = snackBarHostState,
                stackEntryValue = stackEntry
            )
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
            CreateModuleScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.ExamScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) { navBackStackEntry ->
            val stackEntry =
                navBackStackEntry.savedStateHandle.get<SnackbarVisuals>("show-snackBar")
            val viewModel = hiltViewModel<ExamListViewModel>()
            val state by viewModel.uiState.collectAsState()
            ExamListScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                drawerState = drawerState,
                onPopBackStack = { navController.popBackStackSafe() },
                onNavigate = { navController.navigate(it.route) },
                snackBarHostState = snackBarHostState,
                stackEntryValue = stackEntry
            )
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
            CreateExamScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.SchoolEditScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<UpdateSchoolViewModel>()
            val state by viewModel.uiState.collectAsState()
            UpdateSchoolScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.DivisionEditScreen.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<UpdateDivisionViewModel>()
            val state by viewModel.uiState.collectAsState()
            UpdateDivisionScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.ModuleEditScreen.route + "/{id}", arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<UpdateModuleViewModel>()
            val state by viewModel.uiState.collectAsState()
            UpdateModuleScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.ExamEditScreen.route + "/{id}", arguments = listOf(navArgument(
                "id"
            ) {
                type = NavType.StringType
                defaultValue = "None"
                nullable = false
            })
        ) {
            val viewModel = hiltViewModel<UpdateExamViewModel>()
            val state by viewModel.uiState.collectAsState()
            UpdateExamScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                onPopBackStack = { data -> showSnackBarUponPopBackStack(navController, data) },
                snackBarHostState = snackBarHostState
            )
        }
    }
}

private fun showSnackBarUponPopBackStack(navController: NavController, data: SnackbarVisuals?) {
    if (data != null) {
        navController.previousBackStackEntry?.savedStateHandle?.set("show-snackBar", data)
    }
    navController.popBackStackSafe()
}

private fun NavController.popBackStackSafe(): Boolean =
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    } else {
        false
    }
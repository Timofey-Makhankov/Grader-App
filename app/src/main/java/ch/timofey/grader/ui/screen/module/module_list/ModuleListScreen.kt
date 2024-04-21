package ch.timofey.grader.ui.screen.module.module_list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.molecules.BreadCrumb
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.components.organisms.items.ModuleItem
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.navigation.NavigationDrawerItems
import ch.timofey.grader.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun ModuleListScreen(
    drawerState: DrawerState,
    state: ModuleListState,
    onEvent: (ModuleListEvent) -> Unit,
    onPopBackStack: () -> Unit,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackBarHostState: SnackbarHostState,
    stackEntryValue: SnackbarVisuals?
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        if (stackEntryValue != null) {
            this.launch(Dispatchers.Main) {
                snackBarHostState.showSnackbar(stackEntryValue)
            }
        }
    }
    val deletedModuleId = remember { mutableStateOf<UUID?>(value = null) }
    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.Main) {
            uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        onNavigate(event)
                    }

                    is UiEvent.PopBackStack -> {
                        onPopBackStack()
                    }

                    is UiEvent.ShowSnackBar -> {
                        scope.launch(Dispatchers.Main) {
                            val result = snackBarHostState.showSnackbar(
                                message = event.message,
                                withDismissAction = event.withDismissAction,
                                actionLabel = event.action,
                                duration = SnackbarDuration.Long
                            )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    onEvent(ModuleListEvent.OnUndoDeleteClick(deletedModuleId.value!!))
                                }

                                SnackbarResult.Dismissed -> Unit
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
    NavigationDrawer(
        drawerState = drawerState,
        items = NavigationDrawerItems.list,
        currentScreen = Screen.ModuleScreen,
        onItemClick = { menuItem ->
            onEvent(ModuleListEvent.OnDeleteItems(menuItem.onNavigate))
            scope.launch(Dispatchers.Main) {
                drawerState.close()
            }
        },
    ) {
        Scaffold(
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                state.averageGradeIsZero?.let {
                    AnimatedVisibility(visible = it, enter = slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = 200,
                            delayMillis = 100,
                            easing = FastOutSlowInEasing
                        )
                    ) { fullWidth -> -fullWidth / 3 } + fadeIn(
                        animationSpec = tween(
                            durationMillis = 200,
                            delayMillis = 100,
                            easing = FastOutSlowInEasing
                        )
                    ), exit = slideOutHorizontally(
                        animationSpec = tween(
                            durationMillis = 100, easing = FastOutSlowInEasing
                        )
                    ) { fullWidth -> fullWidth / 3 } + fadeOut(
                        animationSpec = tween(
                            durationMillis = 100, easing = FastOutSlowInEasing
                        )
                    )) {
                        ch.timofey.grader.ui.components.atom.FloatingActionButton(
                            modifier = if (!it) Modifier.requiredWidth(0.dp) else Modifier,
                            onFABClick = { onEvent(ModuleListEvent.OnFABClick) },
                            contentDescription = "Create a new Module",
                        )
                    }
                }
            },
            bottomBar = {
                state.averageGradeIsZero?.let {
                    AnimatedVisibility(visible = !it, enter = slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = 200,
                            delayMillis = 100,
                            easing = FastOutSlowInEasing
                        )
                    ) { fullWidth -> -fullWidth / 3 } + fadeIn(
                        animationSpec = tween(
                            durationMillis = 200,
                            delayMillis = 100,
                            easing = FastOutSlowInEasing
                        )
                    ), exit = slideOutHorizontally(
                        animationSpec = tween(
                            durationMillis = 100, easing = FastOutSlowInEasing
                        )
                    ) { fullWidth -> fullWidth / 3 } + fadeOut(
                        animationSpec = tween(
                            durationMillis = 100, easing = FastOutSlowInEasing
                        )
                    )) {
                        ch.timofey.grader.ui.components.organisms.BottomAppBar(
                            text = "Average Grade: ${state.averageGrade}",
                            floatingActionButton = {
                                ch.timofey.grader.ui.components.atom.FloatingActionButton(
                                    onFABClick = { onEvent(ModuleListEvent.OnFABClick) },
                                    contentDescription = "Create a new Exam Card"
                                )
                            })
                    }
                }
            },
            topBar = {
                AppBar(
                    onNavigationIconClick = { onEvent(ModuleListEvent.OnReturnBack) },
                    actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    actionContentDescription = "Go Back to previous Screen",
                    appBarTitle = "Modules",
                    locationIndicator = state.showNavigationIcons?: false,
                    pageIndex = 2
                )
            }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    if (state.locationsTitles.isNotEmpty()) {
                        BreadCrumb(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                            locationTitles = state.locationsTitles
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.extraSmall
                            )
                        )
                    }
                }
                if (state.swipingEnabled != null) {
                    items(items = state.moduleList, key = { module -> module.id }) { module ->
                        ModuleItem(module = module, disableSwipe = !state.swipingEnabled, onCheckBoxClick = {
                            onEvent(
                                ModuleListEvent.OnCheckChange(
                                    module.id, !module.isSelected
                                )
                            )
                        }, onLongClick = {
                            onNavigate(
                                UiEvent.Navigate(
                                    Screen.ExamScreen.withArgs(module.id.toString())
                                )
                            )
                        }, onSwipe = { moduleItem ->
                            deletedModuleId.value = moduleItem.id
                            onEvent(ModuleListEvent.OnSwipeDelete(moduleItem.id))
                        }, onDeleteClick = {
                            deletedModuleId.value = module.id
                            onEvent(ModuleListEvent.OnDeleteButtonClick(module.id))
                        }, onUpdateClick = {
                            onNavigate(
                                UiEvent.Navigate(
                                    Screen.ModuleEditScreen.withArgs(
                                        module.id.toString()
                                    )
                                )
                            )
                        })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ModuleListScreenPreview() {
    GraderTheme {
        ModuleListScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = ModuleListState(
                moduleList = listOf(
                    Module(
                        id = UUID.randomUUID(),
                        divisionId = UUID.randomUUID(),
                        name = LoremIpsum(3).values.joinToString(""),
                        description = LoremIpsum(20).values.joinToString(""),
                        teacherFirstname = "",
                        teacherLastname = ""
                    ), Module(
                        id = UUID.randomUUID(),
                        divisionId = UUID.randomUUID(),
                        name = LoremIpsum(3).values.joinToString(""),
                        description = LoremIpsum(20).values.joinToString(""),
                        teacherFirstname = "",
                        teacherLastname = "",
                        isSelected = true,

                        ), Module(
                        id = UUID.randomUUID(),
                        divisionId = UUID.randomUUID(),
                        name = LoremIpsum(3).values.joinToString(""),
                        description = LoremIpsum(20).values.joinToString(""),
                        teacherFirstname = "",
                        teacherLastname = ""
                    )
                ), locationsTitles = listOf("school item", "division item", "Modules")
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            onNavigate = {},
            snackBarHostState = SnackbarHostState(),
            stackEntryValue =  null
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ModuleListScreenDarkModePreview() {
    GraderTheme {
        ModuleListScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = ModuleListState(
                moduleList = listOf(
                    Module(
                        id = UUID.randomUUID(),
                        divisionId = UUID.randomUUID(),
                        name = LoremIpsum(3).values.joinToString(""),
                        description = LoremIpsum(20).values.joinToString(""),
                        teacherFirstname = "",
                        teacherLastname = ""
                    ), Module(
                        id = UUID.randomUUID(),
                        divisionId = UUID.randomUUID(),
                        name = LoremIpsum(3).values.joinToString(""),
                        description = LoremIpsum(20).values.joinToString(""),
                        teacherFirstname = "",
                        teacherLastname = "",
                        isSelected = true,

                        ), Module(
                        id = UUID.randomUUID(),
                        divisionId = UUID.randomUUID(),
                        name = LoremIpsum(3).values.joinToString(""),
                        description = LoremIpsum(20).values.joinToString(""),
                        teacherFirstname = "",
                        teacherLastname = ""
                    )
                ), locationsTitles = listOf("school item", "division item", "Modules")
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            onNavigate = {},
            snackBarHostState = SnackbarHostState(),
            stackEntryValue = null
        )
    }
}

package ch.timofey.grader.ui.screen.module_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.*
import ch.timofey.grader.ui.components.cards.ModuleCard
import ch.timofey.grader.ui.components.items.ModuleItem
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
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
    snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val deletedModuleId = remember { mutableStateOf<UUID?>(value = null) }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                is UiEvent.ShowSnackBar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = event.withDismissAction,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(ModuleListEvent.OnUndoDeleteClick(deletedModuleId.value!!))
                    }
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
            scope.launch {
                drawerState.close()
            }
        },
    ) {
        Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) },
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
                        FloatingActionButton(
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
                        BottomAppBar(
                            text = "Average Grade: ${state.averageGrade}",
                            floatingActionButton = {
                                FloatingActionButton(
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
                    icon = Icons.Default.ArrowBack,
                    contentDescription = "Go Back to previous Screen",
                    appBarTitle = "Modules"
                )
            }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = state.moduleList, key = { module -> module.id }) { module ->
                    ModuleItem(module = module, onCheckBoxClick = {
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
                    })
                }
            }
        }
    }
}


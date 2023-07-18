package ch.timofey.grader.ui.screen.module_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
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
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.BottomAppBar
import ch.timofey.grader.ui.components.FloatingActionButton
import ch.timofey.grader.ui.components.cards.ModuleCard
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.components.SwipeToDeleteBackground
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
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
    val dismissState = rememberDismissState(
        // This is a Hack, you take the percentage of the the threshold (example: 50%), divide it by 10 and add 1.
        // That's your threshold you have to divide by the value is given, which is the width of your phone in Pixels.
        // In this example I want a 70% Threshold and is equal to 8
        positionalThreshold = { value -> (value / 8).dp.toPx() }
    )
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
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        dismissState.reset()
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
            onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            scope.launch {
                drawerState.close()
            }
        },
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                state.averageGradeIsZero?.let {
                    AnimatedVisibility(
                        visible = it,
                        enter = slideInHorizontally(
                            animationSpec = tween(
                                durationMillis = 200,
                                delayMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        ) { fullWidth -> -fullWidth / 3 }
                                + fadeIn(
                            animationSpec = tween(
                                durationMillis = 200,
                                delayMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        exit = slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        ) { fullWidth -> fullWidth / 3 }
                                + fadeOut(
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        )
                    ) {
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
                    AnimatedVisibility(
                        visible = !it,
                        enter = slideInHorizontally(
                            animationSpec = tween(
                                durationMillis = 200,
                                delayMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        ) { fullWidth -> -fullWidth / 3 }
                                + fadeIn(
                            animationSpec = tween(
                                durationMillis = 200,
                                delayMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        exit = slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        ) { fullWidth -> fullWidth / 3 }
                                + fadeOut(
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = FastOutSlowInEasing
                            )
                        )
                    ) {
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
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = state.moduleList,
                    key = { module -> module.id }
                ) { module ->
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        deletedModuleId.value = module.id
                        onEvent(ModuleListEvent.OnSwipeDelete(module.id))
                    }
                    SwipeToDismiss(
                        modifier = Modifier.padding(vertical = 1.dp),
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color by animateColorAsState(
                                targetValue = when (dismissState.targetValue) {
                                    DismissValue.Default -> MaterialTheme.colorScheme.background
                                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.errorContainer
                                    else -> MaterialTheme.colorScheme.background
                                }, label = "Color"
                            )
                            val isVisible =
                                dismissState.targetValue == DismissValue.DismissedToStart
                            AnimatedVisibility(
                                visible = isVisible,
                                enter = fadeIn(
                                    animationSpec = TweenSpec(
                                        durationMillis = 400
                                    )
                                ),
                                exit = fadeOut(
                                    animationSpec = TweenSpec(
                                        durationMillis = 400
                                    )
                                )
                            ) {
                                SwipeToDeleteBackground(color = color)
                            }
                        },
                        dismissContent = {
                            ModuleCard(
                                modifier = Modifier.padding(MaterialTheme.spacing.small),
                                module = module,
                                onCheckBoxClick = {
                                    onEvent(
                                        ModuleListEvent.OnCheckChange(
                                            module.id,
                                            !module.isSelected
                                        )
                                    )
                                },
                                onLongClick = {
                                    onNavigate(
                                        UiEvent.Navigate(
                                            Screen.ExamScreen.withArgs(module.id.toString())
                                        )
                                    )
                                }
                            )
                        }
                    )
                }
            }

        }

    }
}

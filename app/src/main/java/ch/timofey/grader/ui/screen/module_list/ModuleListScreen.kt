package ch.timofey.grader.ui.screen.module_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.FloatingActionButton
import ch.timofey.grader.ui.components.ModuleCard
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.components.SwipeToDeleteBackground
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleListScreen(
    drawerState: DrawerState,
    state: ModuleListState,
    onEvent: (ModuleListEvent) -> Unit,
    onPopBackStack: () -> Unit,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                else -> Unit
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
        FloatingActionButton(
            onFABClick = { /*TODO*/ },
            contentDescription = "Create a new Module",
            appBar = {
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
                    val dismissState = rememberDismissState(positionalThreshold = { 65.dp.toPx() })
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        onEvent(ModuleListEvent.OnSwipeDelete(module))
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
                                grade = 0.0,
                                onCheckBoxClick = { onEvent(ModuleListEvent.OnCheckChange(module.id, !module.isSelected)) },
                                onLongClick = { /*TODO*/ }
                            )
                        }
                    )
                }
            }
        }
    }
}
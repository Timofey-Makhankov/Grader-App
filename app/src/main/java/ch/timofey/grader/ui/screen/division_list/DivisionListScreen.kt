package ch.timofey.grader.ui.screen.division_list

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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.*
import ch.timofey.grader.ui.components.cards.DivisionCard
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionListScreen(
    drawerState: DrawerState,
    state: DivisionListState,
    onEvent: (DivisionListEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val deletedDivisionId = remember { mutableStateOf<UUID?>(null) }
    val dismissState = rememberDismissState(
        //This is a Hack, you take the percentage of the the threshold (example: 50%), divide it by 10 and add 1.
        //that's your threshold you have to divide by the value is given, which is the width of your phone in Pixels.
        //In this example I want a 70% Threshold and is equal to 8
        positionalThreshold = { value -> (value / 8).dp.toPx() })
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
                        actionLabel = event.action,
                        withDismissAction = event.withDismissAction
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        dismissState.reset()
                        onEvent(DivisionListEvent.OnUndoDeleteClick(deletedDivisionId.value!!))
                    }
                }
            }
        }
    }
    NavigationDrawer(drawerState = drawerState,
        currentScreen = Screen.DivisionScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            println("Clicked on ${menuItem.title}")
            onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            scope.launch {
                drawerState.close()
            }
        }) {
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
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
                            onFABClick = { onEvent(DivisionListEvent.OnCreateDivision) },
                            contentDescription = "Create a new Division",
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
                        BottomAppBar(text = "Average Grade: ${state.averageGrade}",
                            floatingActionButton = {
                                FloatingActionButton(
                                    onFABClick = { onEvent(DivisionListEvent.OnCreateDivision) },
                                    contentDescription = "Create a new Exam Card"
                                )
                            })
                    }
                }
            },
            topBar = {
                AppBar(
                    onNavigationIconClick = { onEvent(DivisionListEvent.OnReturnBack) },
                    icon = Icons.Default.ArrowBack,
                    contentDescription = "Go Back to previous Screen",
                    appBarTitle = "Divisions"
                )
            }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = state.divisionList, key = { division -> division.id }) { division ->
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        deletedDivisionId.value = division.id
                        onEvent(DivisionListEvent.OnSwipeDelete(division.id))
                    }
                    SwipeToDismiss(modifier = Modifier.padding(vertical = 1.dp),
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color by animateColorAsState(
                                targetValue = when (dismissState.targetValue) {
                                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.errorContainer
                                    else -> MaterialTheme.colorScheme.background
                                }, label = "Color"
                            )
                            val isVisible =
                                dismissState.targetValue == DismissValue.DismissedToStart
                            AnimatedVisibility(
                                visible = isVisible, enter = fadeIn(
                                    animationSpec = TweenSpec(
                                        durationMillis = 400
                                    )
                                ), exit = fadeOut(
                                    animationSpec = TweenSpec(
                                        durationMillis = 400
                                    )
                                )
                            ) {
                                SwipeToDeleteBackground(color = color)
                            }
                        },
                        dismissContent = {
                            DivisionCard(modifier = Modifier.padding(MaterialTheme.spacing.small),
                                division = division,
                                onCheckBoxClick = {
                                    onEvent(
                                        DivisionListEvent.OnCheckChange(
                                            id = division.id, value = !division.isSelected
                                        )
                                    )
                                },
                                onLongClick = {
                                    onNavigate(
                                        UiEvent.Navigate(
                                            Screen.ModuleScreen.withArgs(
                                                division.id.toString()
                                            )
                                        )
                                    )
                                })
                        })
                }
            }
        }
    }
}


@Preview
@Composable
private fun DivisionListScreenPreview() {
    GraderTheme {
        DivisionListScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = DivisionListState(
                divisionList = listOf(
                    Division(
                        id = UUID.randomUUID(),
                        name = "Semester 1",
                        description = "Lorem Impsum",
                        schoolYear = 2023,
                        schoolId = UUID.randomUUID(),
                        grade = 0.0
                    ), Division(
                        id = UUID.randomUUID(),
                        name = "Semester 2",
                        description = "Lorem Impsum",
                        schoolYear = 2002,
                        schoolId = UUID.randomUUID(),
                        grade = 0.0
                    ), Division(
                        id = UUID.randomUUID(),
                        name = "Semester 3",
                        description = "Lorem Impsum",
                        schoolYear = 2222,
                        schoolId = UUID.randomUUID(),
                        grade = 0.0
                    )
                )
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}
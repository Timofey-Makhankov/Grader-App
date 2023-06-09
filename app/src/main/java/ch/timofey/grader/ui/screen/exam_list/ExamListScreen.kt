package ch.timofey.grader.ui.screen.exam_list

import android.content.res.Configuration
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.BottomAppBar
import ch.timofey.grader.ui.components.cards.ExamCard
import ch.timofey.grader.ui.components.FloatingActionButton
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.components.SwipeToDeleteBackground
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamListScreen(
    state: ExamListState,
    onEvent: (ExamListEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    drawerState: DrawerState,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
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
        drawerState = drawerState, items = NavigationDrawerItems.list, onItemClick = { menuItem ->
            onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            scope.launch {
                drawerState.close()
            }
        }, currentScreen = Screen.ExamScreen
    ) {
        Scaffold(
            floatingActionButtonPosition = FabPosition.End,
            topBar = {
                AppBar(
                    onNavigationIconClick = { onEvent(ExamListEvent.OnBackButtonClick) },
                    icon = Icons.Default.ArrowBack,
                    contentDescription = "Go Back to previous Screen",
                    appBarTitle = "Exams"
                )
            }, bottomBar = {
                state.averageGradeIsZero?.let {
                    AnimatedVisibility(
                        visible = !it,
                        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing)){fullWidth -> -fullWidth / 3 }
                                + fadeIn(animationSpec = tween(durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing)),
                        exit = slideOutHorizontally(animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)){fullWidth -> fullWidth / 3 }
                                + fadeOut(animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing))
                    ) {
                        BottomAppBar(
                            text = "Average Grade: ${state.averageGrade}",
                            floatingActionButton = {
                                FloatingActionButton(
                                    onFABClick = { onEvent(ExamListEvent.OnFABClick) },
                                    contentDescription = "Create a new Exam Card"
                                )
                            })
                    }
                }
            }, floatingActionButton = {
                state.averageGradeIsZero?.let {
                    AnimatedVisibility(
                        visible = it,
                        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing)){fullWidth -> -fullWidth / 3 }
                                + fadeIn(animationSpec = tween(durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing)),
                        exit = slideOutHorizontally(animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)){fullWidth -> fullWidth / 3 }
                                + fadeOut(animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing))
                    ) {
                        FloatingActionButton(
                            modifier = if (!it) Modifier.requiredWidth(0.dp) else Modifier,
                            onFABClick = { onEvent(ExamListEvent.OnFABClick) },
                            contentDescription = "Create a new Exam Card"
                        )
                    }
                }
            }) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = state.exams, key = { exam -> exam.id }) { exam ->
                    val dismissState =
                        rememberDismissState(positionalThreshold = { value -> (value / 8).dp.toPx() })
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        onEvent(ExamListEvent.OnSwipeDelete(exam))
                    }
                    SwipeToDismiss(modifier = Modifier.padding(vertical = 1.dp),
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color by animateColorAsState(
                                targetValue = when (dismissState.targetValue) {
                                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.errorContainer
                                    else -> MaterialTheme.colorScheme.background
                                }, label = "Dismiss Color Change"
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
                            ExamCard(modifier = Modifier.padding(MaterialTheme.spacing.small),
                                exam = exam,
                                onCheckBoxClick = {
                                    onEvent(
                                        ExamListEvent.OnCheckChange(
                                            id = exam.id, value = !exam.isSelected
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
private fun ExamListScreenPreview() {
    GraderTheme {
        ExamListScreen(
            state = ExamListState(
                averageGradeIsZero = true,
                averageGrade = ""
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onPopBackStack = {},
            onNavigate = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExamListScreenDarkModePreview() {
    GraderTheme {
        ExamListScreen(
            state = ExamListState(
                averageGradeIsZero = false,
                averageGrade = "5.6"
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onPopBackStack = {},
            onNavigate = {}
        )
    }
}
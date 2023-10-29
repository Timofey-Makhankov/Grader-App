package ch.timofey.grader.ui.screen.exam.exam_list

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.molecules.BreadCrumb
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.organisms.items.ExamItem
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun ExamListScreen(
    state: ExamListState,
    onEvent: (ExamListEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    drawerState: DrawerState,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val deletedExamId = remember { mutableStateOf<UUID?>(null) }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    onNavigate(event)
                }

                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        val result = snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action,
                            withDismissAction = event.withDismissAction
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            onEvent(ExamListEvent.OnUndoDeleteClick(deletedExamId.value!!))
                        }
                    }
                }
            }
        }
    }
    NavigationDrawer(
        drawerState = drawerState, items = NavigationDrawerItems.list, onItemClick = { menuItem ->
            onEvent(ExamListEvent.OnDeleteItems(menuItem.onNavigate))
            scope.launch {
                drawerState.close()
            }
        }, currentScreen = Screen.ExamScreen
    ) {
        Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) },
            floatingActionButtonPosition = FabPosition.End,
            topBar = {
                AppBar(
                    onNavigationIconClick = { onEvent(ExamListEvent.OnBackButtonClick) },
                    actionIcon = Icons.Default.ArrowBack,
                    actionContentDescription = "Go Back to previous Screen",
                    appBarTitle = "Exams",
                    locationIndicator = true,
                    pageIndex = 3
                )
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
                                    onFABClick = { onEvent(ExamListEvent.OnFABClick) },
                                    contentDescription = "Create a new Exam Card"
                                )
                            })
                    }
                }
            },
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
                item {
                    if (state.locationsTitles.isNotEmpty()) {
                        BreadCrumb(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                            locationTitles = state.locationsTitles
                        )
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.extraSmall
                            )
                        )
                    }
                }
                items(items = state.exams, key = { exam -> exam.id }) { exam ->
                    ExamItem(exam = exam, onSwipe = { examItem ->
                        deletedExamId.value = examItem.id
                        onEvent(ExamListEvent.OnSwipeDelete(examItem.id))
                    }, onCheckBoxClick = {
                        onEvent(
                            ExamListEvent.OnCheckChange(
                                id = exam.id, value = !exam.isSelected
                            )
                        )
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
                averageGrade = "",
                locationsTitles = listOf("School item", "division item", "module item", "Exams")
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onPopBackStack = {},
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExamListScreenDarkModePreview() {
    GraderTheme {
        ExamListScreen(
            state = ExamListState(
                averageGradeIsZero = false, averageGrade = "5.6"
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onPopBackStack = {},
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}
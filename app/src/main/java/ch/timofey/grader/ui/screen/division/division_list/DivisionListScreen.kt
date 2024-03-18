package ch.timofey.grader.ui.screen.division.division_list

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
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.unit.dp
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.molecules.BreadCrumb
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.components.organisms.items.DivisionItem
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun DivisionListScreen(
    drawerState: DrawerState,
    state: DivisionListState,
    onEvent: (DivisionListEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackBarHostState: SnackbarHostState,
    stackEntryValue: SnackbarVisuals?
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        if (stackEntryValue != null) {
            this.launch {
                snackBarHostState.showSnackbar(stackEntryValue)
            }
        }
    }
    val deletedDivisionId = remember { mutableStateOf<UUID?>(null) }
    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
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
                            withDismissAction = event.withDismissAction,
                            duration = SnackbarDuration.Short
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            onEvent(DivisionListEvent.OnUndoDeleteClick(deletedDivisionId.value!!))
                        }
                    }
                }
            }
        }
    }
    NavigationDrawer(drawerState = drawerState,
        currentScreen = Screen.DivisionScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            onEvent(DivisionListEvent.OnDeleteItems(menuItem.onNavigate))
            scope.launch {
                drawerState.close()
            }
        }) {
        Scaffold(//snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
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
                        ch.timofey.grader.ui.components.organisms.BottomAppBar(text = "Average Grade: ${state.averageGrade}",
                            floatingActionButton = {
                                ch.timofey.grader.ui.components.atom.FloatingActionButton(
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
                    actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    actionContentDescription = "Go Back to previous Screen",
                    appBarTitle = "Divisions",
                    locationIndicator = true,
                    pageIndex = 1
                )
            }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item(key = 0) {
                    if (state.locationTitles.isNotEmpty()) {
                        BreadCrumb(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                            locationTitles = state.locationTitles
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.extraSmall
                            )
                        )
                    }
                }
                items(items = state.divisionList, key = { division -> division.id }) { division ->
                    DivisionItem(division = division,
                        onSwipe = { divisionItem ->
                            deletedDivisionId.value = divisionItem.id
                            onEvent(DivisionListEvent.OnSwipeDelete(divisionItem.id))
                        },
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
                        },
                        onDeleteClick = { DivisionListEvent.OnDeleteIconClick(division.id) },
                        onUpdateClick = {
                            onNavigate(
                                UiEvent.Navigate(
                                    Screen.DivisionEditScreen.withArgs(
                                        division.id.toString()
                                    )
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
                ), locationTitles = listOf("Given School Name", "Divisions")
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DivisionListScreenDarkModePreview() {
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
                ), locationTitles = listOf("Given School Name", "Divisions")
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
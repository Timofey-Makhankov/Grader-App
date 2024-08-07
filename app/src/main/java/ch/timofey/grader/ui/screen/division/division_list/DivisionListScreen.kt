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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.navigation.NavigationDrawerItems
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.molecules.BreadCrumb
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.molecules.SwipeContainer
import ch.timofey.grader.ui.components.molecules.cards.DivisionCard
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.components.organisms.BottomAppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.utils.calculatePointsFromGrade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.UUID
import ch.timofey.grader.R

@Composable
fun DivisionListScreen(
    drawerState: DrawerState,
    state: DivisionListState,
    onEvent: (DivisionListEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackBarHostState: SnackbarHostState,
    savedStateHandle: SavedStateHandle
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        val stackEntry =
            savedStateHandle.get<SnackbarVisuals>("show-snackBar")
        if (stackEntry != null) {
            this.launch(Dispatchers.Main) {
                snackBarHostState.showSnackbar(stackEntry)
            }
            savedStateHandle["show-snackBar"] = null
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
                    scope.launch(Dispatchers.Main) {
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

                else -> Unit
            }
        }
    }
    NavigationDrawer(drawerState = drawerState,
        currentScreen = Screen.DivisionScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            onEvent(DivisionListEvent.OnDeleteItems(menuItem.onNavigate))
            scope.launch(Dispatchers.Main) {
                drawerState.close()
            }
        }) {
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
                            onFABClick = { onEvent(DivisionListEvent.OnCreateDivision) },
                            contentDescription = R.string.create_a_new_division.toString(),
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
                        BottomAppBar(text = stringResource(id = R.string.average_grade) + state.averageGrade,
                            subText = if (state.minimumGrade != null && state.showPoints) {
                                stringResource(id = R.string.points) + " ${
                                    calculatePointsFromGrade(
                                        state.averageGrade.toDouble(), state.minimumGrade.toDouble()
                                    ).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()
                                }"
                            } else null,
                            floatingActionButton = {
                                ch.timofey.grader.ui.components.atom.FloatingActionButton(
                                    onFABClick = { onEvent(DivisionListEvent.OnCreateDivision) },
                                    contentDescription = stringResource(id = R.string.create_a_new_exam_card)
                                )
                            })
                    }
                }
            },
            topBar = {
                AppBar(
                    onNavigationIconClick = { onEvent(DivisionListEvent.OnReturnBack) },
                    actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    actionContentDescription = stringResource(id = R.string.go_back_to_previous_screen),
                    appBarTitle = stringResource(id = R.string.divisions),
                    locationIndicator = state.showNavigationIcons,
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
                items(
                    items = state.divisionList,
                    key = { division -> division.id }) { division ->
                    val expandCard = remember { mutableStateOf(false) }
                    SwipeContainer(
                        modifier = Modifier.padding(MaterialTheme.spacing.small),
                        swipeEnabled = state.swipingEnabled,
                        onSwipe = {
                            deletedDivisionId.value = division.id
                            onEvent(DivisionListEvent.OnSwipeDelete(division.id))
                        }
                    ) {
                        DivisionCard(
                            modifier = Modifier.padding(MaterialTheme.spacing.small),
                            division = division,
                            isOpen = expandCard.value,
                            colorGrade = state.colorGrades,
                            onCheckBoxClick = {
                                onEvent(
                                    DivisionListEvent.OnCheckChange(
                                        id = division.id, value = !division.isSelected
                                    )
                                )
                            },
                            onClick = {
                                if (state.swapNavigation) {
                                    onNavigate(
                                        UiEvent.Navigate(
                                            Screen.ModuleScreen.withArgs(
                                                division.id.toString()
                                            )
                                        )
                                    )
                                } else {
                                    expandCard.value = !expandCard.value
                                }
                                      },
                            onLongClick = {
                                if (state.swapNavigation) {
                                    expandCard.value = !expandCard.value
                                } else {
                                    onNavigate(
                                        UiEvent.Navigate(
                                            Screen.ModuleScreen.withArgs(
                                                division.id.toString()
                                            )
                                        )
                                    )
                                }
                            },
                            onEditClick = {
                                onNavigate(
                                    UiEvent.Navigate(
                                        Screen.DivisionEditScreen.withArgs(
                                            division.id.toString()
                                        )
                                    )
                                )
                            },
                            onDeleteClick = { onEvent(DivisionListEvent.OnDeleteIconClick(division.id)) })
                    }
                }
            }
        }
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
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
            savedStateHandle = SavedStateHandle()
        )
    }
}
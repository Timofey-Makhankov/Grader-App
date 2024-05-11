package ch.timofey.grader.ui.screen.school.school_list

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
import androidx.compose.material.icons.filled.Menu
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
import ch.timofey.grader.R
import ch.timofey.grader.db.AppTheme
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.navigation.NavigationDrawerItems
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.atom.FloatingActionButton
import ch.timofey.grader.ui.components.molecules.BreadCrumb
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.molecules.SwipeContainer
import ch.timofey.grader.ui.components.molecules.cards.SchoolCard
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

@Composable
fun SchoolListScreen(
    drawerState: DrawerState,
    state: SchoolListState,
    onEvent: (SchoolListEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackBarHostState: SnackbarHostState,
    savedStateHandle: SavedStateHandle
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        val stackEntry = savedStateHandle.get<SnackbarVisuals>("show-snackBar")
        if (stackEntry != null) {
            this.launch(Dispatchers.Main) {
                snackBarHostState.showSnackbar(stackEntry)
            }
            savedStateHandle["show-snackBar"] = null
        }
    }
    val deletedSchoolId = remember { mutableStateOf<UUID?>(value = null) }
    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.ShowSnackBar -> {
                    scope.launch(Dispatchers.Main) {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        val result = snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action,
                            withDismissAction = event.withDismissAction,
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            onEvent(SchoolListEvent.OnUndoDeleteClick(deletedSchoolId.value!!))
                        }
                    }
                }
                else -> Unit
            }
        }
    }
    NavigationDrawer(drawerState = drawerState,
        currentScreen = Screen.MainScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            if (menuItem.onNavigate != Screen.MainScreen.route) {
                onEvent(SchoolListEvent.OnDeleteItems(menuItem.onNavigate))
            }
            scope.launch(Dispatchers.Main) {
                drawerState.close()
            }
        }) {
        Scaffold(floatingActionButtonPosition = FabPosition.End, floatingActionButton = {
            state.averageGradeIsZero?.let {
                AnimatedVisibility(visible = it, enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing
                    )
                ) { fullWidth -> -fullWidth / 3 } + fadeIn(
                    animationSpec = tween(
                        durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing
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
                        onFABClick = { onEvent(SchoolListEvent.OnCreateSchool) },
                        contentDescription = "Create a new School",
                    )
                }
            }
        }, bottomBar = {
            state.averageGradeIsZero?.let {
                AnimatedVisibility(visible = !it, enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing
                    )
                ) { fullWidth -> -fullWidth / 3 } + fadeIn(
                    animationSpec = tween(
                        durationMillis = 200, delayMillis = 100, easing = FastOutSlowInEasing
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
                        subText = if (state.minimumGrade != null && state.showPoints) {
                            "Points: ${
                                calculatePointsFromGrade(
                                    state.averageGrade.toDouble(), state.minimumGrade.toDouble()
                                ).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()
                            }"
                        } else null,
                        floatingActionButton = {
                            FloatingActionButton(
                                onFABClick = { onEvent(SchoolListEvent.OnCreateSchool) },
                                contentDescription = "Create a new Exam Card"
                            )
                        })
                }
            }
        }, topBar = {
            AppBar(
                onNavigationIconClick = { scope.launch(Dispatchers.Main) { drawerState.open() } },
                actionIcon = Icons.Default.Menu,
                actionContentDescription = "Toggle Navigation Drawer",
                appBarTitle = stringResource(R.string.school_screen_title),
                locationIndicator = state.showNavigationIcons ?: false,
                pageIndex = 0
            )
        }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    BreadCrumb(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                        locationTitles = listOf("Home")
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.spacing.medium,
                            vertical = MaterialTheme.spacing.extraSmall
                        )
                    )
                }
                items(
                    items = state.schoolList,
                    key = { school -> school.id },
                ) { school ->
                    val expandCard = remember { mutableStateOf(false) }
                    SwipeContainer(
                        modifier = Modifier.padding(MaterialTheme.spacing.small),
                        swipeEnabled = state.swipingEnabled,
                        onSwipe = {
                            deletedSchoolId.value = school.id
                            onEvent(SchoolListEvent.OnSwipeDelete(school.id))
                        }) {
                        SchoolCard(
                            modifier = Modifier.padding(MaterialTheme.spacing.small),
                            school = school,
                            isOpen = expandCard.value,
                            colorGrade = state.colorGrades,
                            onCheckBoxClick = {
                                onEvent(
                                    SchoolListEvent.OnCheckChange(
                                        schoolId = school.id, value = !school.isSelected
                                    )
                                )
                            },
                            onClick = {
                                if (state.swapNavigation) {
                                    onNavigate(
                                        UiEvent.Navigate(
                                            Screen.DivisionScreen.withArgs(
                                                school.id.toString()
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
                                            Screen.DivisionScreen.withArgs(
                                                school.id.toString()
                                            )
                                        )
                                    )
                                }
                            },
                            onEditClick = {
                                onNavigate(
                                    UiEvent.Navigate(
                                        Screen.SchoolEditScreen.withArgs(
                                            school.id.toString()
                                        )
                                    )
                                )
                            },
                            onDeleteClick = {
                                onEvent(
                                    SchoolListEvent.OnItemClickDelete(
                                        schoolId = school.id
                                    )
                                )
                            })
                    }
                }

            }

        }
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun PreviewMainScreen() {
    GraderTheme(
        themeSetting = AppTheme.DEVICE_THEME
    ) {
        SchoolListScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onEvent = {},
            state = SchoolListState(
                listOf(
                    School(
                        id = UUID.randomUUID(),
                        name = "Technische Berufsschule Zürich",
                        description = "Eine Schule für Informatikern",
                        address = "Ausstellungsstrasse 70",
                        zipCode = "8005",
                        city = "Zürich",
                        grade = 0.0
                    ), School(
                        id = UUID.randomUUID(),
                        name = "Schulhaus Riedenhalden",
                        description = null,
                        address = "Riedenhaldenstrasse 12",
                        zipCode = "8046",
                        city = "Zürich",
                        grade = 0.0
                    ), School(
                        id = UUID.randomUUID(),
                        name = "Berufsmaturitätsschule Zürich",
                        description = null,
                        address = "",
                        zipCode = "",
                        city = "Zürich",
                        grade = 0.0
                    ), School(
                        id = UUID.randomUUID(),
                        name = "Noser Young",
                        description = null,
                        address = "Herostrasse 12",
                        zipCode = "",
                        city = "Zürich",
                        grade = 0.0
                    )
                ), showNavigationIcons = true, colorGrades = true, averageGradeIsZero = false
            ),
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = SnackbarHostState(),
            savedStateHandle = SavedStateHandle()
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
)
@Composable
private fun PreviewMainScreenDarkMode() {
    GraderTheme {
        SchoolListScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            onEvent = {},
            state = SchoolListState(averageGradeIsZero = false),
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = SnackbarHostState(),
            savedStateHandle = SavedStateHandle()
        )
    }
}
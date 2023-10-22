package ch.timofey.grader.ui.screen.school_list

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
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.*
import ch.timofey.grader.ui.components.cards.SchoolCard
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun SchoolListScreen(
    drawerState: DrawerState,
    state: SchoolListState,
    onEvent: (SchoolListEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val deletedSchoolId = remember { mutableStateOf<UUID?>(value = null) }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                is UiEvent.ShowSnackBar -> {
                    println("Inside show snack bar")
                    println(snackBarHostState.currentSnackbarData)
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        withDismissAction = event.withDismissAction,
                        duration = SnackbarDuration.Short
                    )
                    println(snackBarHostState.currentSnackbarData)
                    println("after show snackbar")
                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(SchoolListEvent.OnUndoDeleteClick(deletedSchoolId.value!!))
                    }

                    println("end of show snack bar")
                }

                else -> Unit
            }
        }
    }
    NavigationDrawer(drawerState = drawerState,
        currentScreen = Screen.MainScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            println("Clicked on ${menuItem.title}")
            if (menuItem.onNavigate != Screen.MainScreen.route) {
                onEvent(SchoolListEvent.OnDeleteItems(menuItem.onNavigate))
            }
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
                            onFABClick = { onEvent(SchoolListEvent.OnCreateSchool) },
                            contentDescription = "Create a new School",
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
                                    onFABClick = { onEvent(SchoolListEvent.OnCreateSchool) },
                                    contentDescription = "Create a new Exam Card"
                                )
                            })
                    }
                }
            },
            topBar = {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    actionIcon = Icons.Default.Menu,
                    actionContentDescription = "Toggle Drawer",
                    appBarTitle = "Schools",
                    locationIndicator = true,
                    pageIndex = 0
                )
            }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = state.schoolList,
                    key = { school -> school.id },
                ) { school ->
                    SchoolCard(
                        modifier = Modifier.padding(MaterialTheme.spacing.small),
                        onCheckBoxClick = {
                            onEvent(
                                SchoolListEvent.OnCheckChange(
                                    schoolId = school.id, value = !school.isSelected
                                )
                            )
                        },
                        onLongClick = {
                            onNavigate(
                                UiEvent.Navigate(
                                    Screen.DivisionScreen.withArgs(
                                        school.id.toString()
                                    )
                                )
                            )
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
                        },
                        school = school
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMainScreen() {
    GraderTheme {
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
                )
            ),
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
    showSystemUi = false
)
@Composable
private fun PreviewMainScreenDarkMode() {
    GraderTheme {
        SchoolListScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            onEvent = {},
            state = SchoolListState(),
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}
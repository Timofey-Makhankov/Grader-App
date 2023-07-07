package ch.timofey.grader.ui.screen.division_list

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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.cards.DivisionCard
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
        currentScreen = Screen.DivisionScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            println("Clicked on ${menuItem.title}")
            onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            scope.launch {
                drawerState.close()
            }
        }) {
        FloatingActionButton(
            onFABClick = { onEvent(DivisionListEvent.OnCreateDivision) },
            contentDescription = "Create a new Division",
            appBar = {
                AppBar(
                    onNavigationIconClick = { onEvent(DivisionListEvent.OnReturnBack) },
                    icon = Icons.Default.ArrowBack,
                    contentDescription = "Go Back to previous Screen",
                    appBarTitle = "Divisions"
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
                    items = state.divisionList,
                    key = { division -> division.id }
                ) { division ->
                    val dismissState = rememberDismissState(
                        //This is a Hack, you take the percentage of the the threshold (example: 50%), divide it by 10 and add 1.
                        //that's your threshold you have to divide by the value is given, which is the width of your phone in Pixels.
                        //In this example I want a 70% Threshold and is equal to 8
                        positionalThreshold = { value -> (value / 8).dp.toPx() }
                    )
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        onEvent(DivisionListEvent.OnSwipeDelete(division))
                    }
                    SwipeToDismiss(
                        modifier = Modifier.padding(vertical = 1.dp),
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color by animateColorAsState(
                                targetValue = when (dismissState.targetValue) {
                                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.errorContainer
                                    else -> MaterialTheme.colorScheme.background
                                },
                                label = "Color"
                            )
                            val isVisible = dismissState.targetValue == DismissValue.DismissedToStart
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
                            DivisionCard(
                                modifier = Modifier.padding(MaterialTheme.spacing.small),
                                division = division,
                                grade = 0.0,
                                onCheckBoxClick = {
                                    onEvent(
                                        DivisionListEvent.OnCheckChange(
                                            id = division.id,
                                            value = !division.isSelected
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
                                }
                            )
                        }
                    )
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
                        schoolId = UUID.randomUUID()
                    ),
                    Division(
                        id = UUID.randomUUID(),
                        name = "Semester 2",
                        description = "Lorem Impsum",
                        schoolYear = 2002,
                        schoolId = UUID.randomUUID()
                    ),
                    Division(
                        id = UUID.randomUUID(),
                        name = "Semester 3",
                        description = "Lorem Impsum",
                        schoolYear = 2222,
                        schoolId = UUID.randomUUID()
                    )
                )
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            onNavigate = {}
        )
    }
}
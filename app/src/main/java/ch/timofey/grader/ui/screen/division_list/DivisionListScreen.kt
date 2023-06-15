package ch.timofey.grader.ui.screen.division_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.DivisionCard
import ch.timofey.grader.ui.components.FloatingActionButton
import ch.timofey.grader.ui.components.NavigationDrawer
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
                items(items = state.divisionList) { division ->
                    DivisionCard(
                        modifier = Modifier.padding(MaterialTheme.spacing.small),
                        division = division,
                        grade = 0.0,
                        onCheckBoxClick = { /*TODO*/ },
                        onLongClick = { /*TODO*/ }
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
            onPopBackStack = { /*TODO*/ },
            onNavigate = {}
        )
    }
}
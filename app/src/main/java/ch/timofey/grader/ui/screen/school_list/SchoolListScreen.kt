package ch.timofey.grader.ui.screen.school_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.FloatingActionButton
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.components.SchoolCard
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
    snackBarHostState: SnackbarHostState,
    onEvent: (SchoolListEvent) -> Unit,
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

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }

                else -> Unit
            }
        }
    }
    NavigationDrawer(
        drawerState = drawerState,
        currentScreen = Screen.MainScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            println("Clicked on ${menuItem.title}")
            if (menuItem.onNavigate != Screen.MainScreen.route) {
                onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            }
            scope.launch {
                drawerState.close()
            }
        }) {
        FloatingActionButton(
            onFABClick = { onEvent(SchoolListEvent.OnCreateSchool) },
            contentDescription = "Create a new School",
            appBar = {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    icon = Icons.Default.Menu,
                    contentDescription = "Toggle Drawer",
                    appBarTitle = "Schools"
                )
            }
        )
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = state.schoolList) { school ->
                    SchoolCard(
                        modifier = Modifier.padding(MaterialTheme.spacing.small),
                        grade = 0.0,
                        school = school,
                        onCheckBoxClick = {
                            onEvent(
                                SchoolListEvent.OnCheckChange(
                                    id = school.id,
                                    value = !school.isSelected
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
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen() {
    GraderTheme {
        SchoolListScreen(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
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
                    ), School(
                        id = UUID.randomUUID(),
                        name = "Schulhaus Riedenhalden",
                        description = null,
                        address = "Riedenhaldenstrasse 12",
                        zipCode = "8046",
                        city = "Zürich"
                    ), School(
                        id = UUID.randomUUID(),
                        name = "Berufsmaturitätsschule Zürich",
                        description = null,
                        address = "",
                        zipCode = "",
                        city = "Zürich"
                    ), School(
                        id = UUID.randomUUID(),
                        name = "Noser Young",
                        description = null,
                        address = "Herostrasse 12",
                        zipCode = "",
                        city = "Zürich"
                    )
                )
            ),
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = remember { SnackbarHostState() })
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
            snackBarHostState = remember { SnackbarHostState() })
    }
}
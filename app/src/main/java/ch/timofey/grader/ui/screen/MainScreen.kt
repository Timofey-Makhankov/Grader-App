package ch.timofey.grader.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.ui.event.MainEvent
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.state.MainState
import ch.timofey.grader.ui.components.FloatingActionButton
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.components.SchoolCard
import ch.timofey.grader.ui.event.UiEvent
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    drawerState: DrawerState,
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true){
        uiEvent.collect{event ->
            when(event){
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
        items = NavigationDrawerItems.getNavigationDrawerItems(),
        onItemClick = {
            println("Clicked on ${it.title}")
        }
    ) {
        FloatingActionButton(
            onFABClick = { onEvent(MainEvent.OnCreateSchool) },
            onAppBarClick = { 
                scope.launch {
                    drawerState.open()
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               items(items = state.schoolList){school ->
                   SchoolCard(
                       modifier = Modifier.padding(MaterialTheme.spacing.small),
                       title = school.name,
                       grade = 0.0,
                       description = school.description
                   )
               }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    GraderTheme {
        MainScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onEvent = {},
            state = MainState(
                listOf(
                    School(
                        id = UUID.randomUUID(),
                        name = "",
                        description = null,
                        address = "",
                        zipCode = "",
                        city = ""
                    ),
                    School(
                        id = UUID.randomUUID(),
                        name = "",
                        description = null,
                        address = "",
                        zipCode = "",
                        city = ""
                    ),
                    School(
                        id = UUID.randomUUID(),
                        name = "",
                        description = null,
                        address = "",
                        zipCode = "",
                        city = ""
                    ),
                    School(
                        id = UUID.randomUUID(),
                        name = "",
                        description = null,
                        address = "",
                        zipCode = "",
                        city = ""
                    )
                )
            ),
            uiEvent = emptyFlow(),
            onNavigate = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = false
)
@Composable
fun PreviewMainScreenDarkMode() {
    GraderTheme {
        MainScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            onEvent = {},
            state = MainState(),
            uiEvent = emptyFlow(),
            onNavigate = {}
        )
    }
}
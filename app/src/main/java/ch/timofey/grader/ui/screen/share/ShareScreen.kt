package ch.timofey.grader.ui.screen.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.Greeting
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ShareScreen(
    drawerState: DrawerState,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit
){
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                else -> Unit
            }
        }
    }
    NavigationDrawer(
        drawerState = drawerState,
        items = NavigationDrawerItems.getNavigationDrawerItems(),
        onItemClick = { menuItem ->
            if (menuItem.onNavigate != Screen.ShareScreen.route){
                onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            }
            scope.launch {
                drawerState.close()
            }
        },
        currentScreen = Screen.ShareScreen
    ) {
        Scaffold (
            topBar = {
                AppBar(
                    onNavigationIconClick = { scope.launch { drawerState.open() } },
                    icon = Icons.Default.Menu,
                    contentDescription = "Toggle Drawer"
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Greeting(name = "Share Screen")
            }
        }
    }
}
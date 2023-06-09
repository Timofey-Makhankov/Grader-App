package ch.timofey.grader.ui.screen.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun ShareScreen(
    drawerState: DrawerState,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
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
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            if (menuItem.onNavigate != Screen.ShareScreen.route) {
                onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            }
            scope.launch {
                drawerState.close()
            }
        },
        currentScreen = Screen.ShareScreen
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    onNavigationIconClick = { scope.launch { drawerState.open() } },
                    icon = Icons.Default.Menu,
                    contentDescription = "Toggle Drawer",
                    appBarTitle = "Share"
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().height(MaterialTheme.spacing.large))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "If you like the App, you can share it with others"
                )
            }
        }
    }
}

@Preview
@Composable
fun ShareScreenPreview(){
    GraderTheme {
        ShareScreen(
            drawerState = DrawerState(initialValue = DrawerValue.Closed),
            uiEvent = emptyFlow(),
            onNavigate = {}
        )
    }
}
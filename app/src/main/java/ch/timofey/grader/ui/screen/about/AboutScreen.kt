package ch.timofey.grader.ui.screen.about

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.timofey.grader.R
import ch.timofey.grader.navigation.NavigationDrawerItems
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun AboutScreen(
    drawerState: DrawerState,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onEvent: (AboutEvent) -> Unit,
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
        drawerState = drawerState, items = NavigationDrawerItems.list, onItemClick = { menuItem ->
            if (menuItem.onNavigate != Screen.ShareScreen.route) {
                onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            }
            scope.launch(Dispatchers.Main) {
                drawerState.close()
            }
        }, currentScreen = Screen.ShareScreen
    ) {
        Scaffold(topBar = {
            AppBar(
                onNavigationIconClick = { scope.launch(Dispatchers.Main) { drawerState.open() } },
                actionIcon = Icons.Default.Menu,
                actionContentDescription = "Toggle Drawer",
                appBarTitle = "About"
            )
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.spacing.large)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    textAlign = TextAlign.Center,
                    text = "If you like the App, you can share it with others Or Donate me on Ko-Fi"
                )
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.extremeLarge),
                    onClick = { onEvent(AboutEvent.OnButtonSourceCLick) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Github Repo")
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                        Icon(
                            painter = painterResource(id = R.drawable.github_mark),
                            contentDescription = "Github Icon"
                        )
                    }
                }
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.extremeLarge),
                    onClick = { onEvent(AboutEvent.OnButtonShareClick) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Share")
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                        Icon(
                            imageVector = Icons.Default.Share, contentDescription = "Share Icon"
                        )
                    }
                }
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.large).padding(horizontal = MaterialTheme.spacing.extraLarge),
                    onClick = { onEvent(AboutEvent.OnButtonDonateClick) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Donate to Ko-Fi")
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                        //val vector = ImageVector.vectorResource(id = R.drawable.kofi_icon)
                        //val painter = rememberVectorPainter(image = vector)
                        Icon(
                            //modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.kofi_icon),
                            contentDescription = "Ko-Fi Icon"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ShareScreenPreview() {
    GraderTheme {
        AboutScreen(drawerState = DrawerState(initialValue = DrawerValue.Closed),
            uiEvent = emptyFlow(),
            onNavigate = {},
            onEvent = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShareScreenDarkModePreview() {
    GraderTheme {
        AboutScreen(drawerState = DrawerState(initialValue = DrawerValue.Closed),
            uiEvent = emptyFlow(),
            onNavigate = {},
            onEvent = {})
    }
}
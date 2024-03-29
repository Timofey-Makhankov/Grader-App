package ch.timofey.grader.ui.screen.about

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import ch.timofey.grader.R
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.DeviceInfo
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun AboutScreen(
    drawerState: DrawerState,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onEvent: (AboutEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val createBackup = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) {uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        context.contentResolver.openOutputStream(uri)?.use {file: OutputStream ->
            file.bufferedWriter().use { it.write("This file was created from the Application") }
        }
    }

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
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "If you like the App, you can share it with others"
                )
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.extremeLarge),
                    onClick = { onEvent(AboutEvent.OnButtonSourceCLick) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Source Code")
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
                    .padding(horizontal = MaterialTheme.spacing.extremeLarge),
                    onClick = { /*onEvent(AboutEvent.OnButtonCreateClick)*/ createBackup.launch("from-application.txt") }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Create Document")
                    }
                }
                Text(text = "${android.os.Build.BRAND} - ${android.os.Build.MODEL} - ${android.os.Build.DEVICE}")
                Text(text = "${CalendarLocale.getDefault()}")
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
package ch.timofey.grader.ui.screen.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.db.AppTheme
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.molecules.SwitchText
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val scope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }
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
    NavigationDrawer(drawerState = drawerState,
        currentScreen = Screen.SettingsScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            println("Clicked on ${menuItem.title}")
            if (menuItem.onNavigate != Screen.SettingsScreen.route) {
                onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            }
            scope.launch {
                drawerState.close()
            }
        }) {
        Scaffold(topBar = {
            AppBar(
                onNavigationIconClick = { scope.launch { drawerState.open() } },
                actionIcon = Icons.Default.Menu,
                actionContentDescription = "Toggle Drawer",
                appBarTitle = "Settings"
            )
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                ), onClick = { /* TODO */ }) {
                    Text(text = "Clear Data")
                }
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = !expanded.value }) {
                    OutlinedTextField(
                        value = state.appTheme.title,
                        onValueChange = { },
                        label = {
                            Text(
                                text = "App Theme"
                            )
                        },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },

                        ) {
                        AppTheme.entries.filter { value -> value != state.appTheme }
                            .forEach { theme ->
                                DropdownMenuItem(text = { Text(text = theme.title) }, onClick = {
                                    onEvent(SettingsEvent.OnThemeChange(theme))
                                    expanded.value = false
                                })
                            }
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = "Data",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
                    )
                }
                Divider(
                    modifier = Modifier.padding(
                        horizontal = MaterialTheme.spacing.large,
                        vertical = MaterialTheme.spacing.small
                    )
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnCalculatePointsChange(value)) },
                    value = state.calculatePointsState,
                    name = "Calculate Points"
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnDoublePointsChange(value)) },
                    value = state.doublePointsState,
                    enabled = state.calculatePointsState,
                    name = "Double Points",
                    extraInfo = if (!state.calculatePointsState) "Enable 'Calculate Points' to enable setting" else ""
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    GraderTheme {
        SettingsScreen(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = SettingsState(appTheme = AppTheme.DEVICE_THEME, calculatePointsState = true),
            onEvent = {},
            uiEvent = emptyFlow(),
            onNavigate = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenDarkModePreview() {
    GraderTheme {
        SettingsScreen(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = SettingsState(appTheme = AppTheme.LIGHT_MODE),
            onEvent = {},
            uiEvent = emptyFlow(),
            onNavigate = {})
    }
}
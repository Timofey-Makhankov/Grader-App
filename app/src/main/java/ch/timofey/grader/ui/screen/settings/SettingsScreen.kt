package ch.timofey.grader.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.Greeting
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit
){
    val scope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }
    val value = remember { mutableStateOf("English") }
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
        currentScreen = Screen.SettingsScreen,
        items = NavigationDrawerItems.getNavigationDrawerItems(),
        onItemClick = { menuItem ->
            println("Clicked on ${menuItem.title}")
            if (menuItem.onNavigate != Screen.SettingsScreen.route){
                onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            }
            scope.launch{
                drawerState.close()
            }
        }
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
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = { value -> expanded.value = value }
                ) {
                    TextField(
                        value = value.value,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "English") },
                            onClick = {
                                value.value = "English"
                                expanded.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Deutsch") },
                            onClick = {
                                value.value = "Deutsch"
                                expanded.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Francais") },
                            onClick = {
                                value.value = "Francais"
                                expanded.value = false
                            }
                        )
                    }
                }
            }
        }
    }
}
package ch.timofey.grader.ui.screen.settings

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ch.timofey.grader.db.AppTheme
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.molecules.InformationDialog
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.molecules.SwitchText
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.screen.school.school_list.SchoolListEvent
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.DeviceInfo
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val scope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val createReport =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult
            val reportData = Json.encodeToString(DeviceInfo.serializer(), DeviceInfo())
            context.contentResolver.openOutputStream(uri)?.use { file: OutputStream ->
                file.bufferedWriter().use { it.write(reportData) }
            }
        }
    val loadBackup =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult
            context.contentResolver.openInputStream(uri)?.use { file: InputStream ->
                onEvent(SettingsEvent.OnLoadBackupFile(file))
            }
        }
    val createBackup =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult
            Log.d("SettingsScreen", uri.pathSegments[1])
            context.contentResolver.openOutputStream(uri)?.use { file: OutputStream ->
                onEvent(SettingsEvent.OnCreateBackupFile(file, uri.pathSegments[1].split(":")[1]))
            }
        }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }

                is UiEvent.ShowSnackBar -> {
                    scope.launch(Dispatchers.Main) {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action,
                            withDismissAction = event.withDismissAction,
                            duration = SnackbarDuration.Short
                        )
                    }
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
            scope.launch(Dispatchers.Main) {
                drawerState.close()
            }
        }) {
        Scaffold(topBar = {
            AppBar(
                onNavigationIconClick = { scope.launch(Dispatchers.Main) { drawerState.open() } },
                actionIcon = Icons.Default.Menu,
                actionContentDescription = "Toggle Drawer",
                appBarTitle = "Settings"
            )
        }) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "App Styling",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.large),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.spacing.small,
                            bottom = MaterialTheme.spacing.large,
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        )
                )
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
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value ->
                        onEvent(
                            SettingsEvent.OnEnableSwipeToDeleteChange(
                                value
                            )
                        )
                    },
                    value = state.enableSwipeToDelete,
                    name = "Enable Swipe Right for Deletion",
                    dialog = {
                        InformationDialog(
                            onDismiss = { it() },
                            text = "Enable Left Swipe to Delete a given on all List Screens. Upon deletion, it will show you a Snack bar of the deleted Item and can be undone"
                        )
                    },
                    showExtraInformation = true
                )
                Spacer(Modifier.height(MaterialTheme.spacing.large))
                Text(
                    "Data Manipulation",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.large),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.spacing.small,
                            bottom = MaterialTheme.spacing.large,
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        )
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnCalculatePointsChange(value)) },
                    value = state.calculatePointsState,
                    name = "Calculate Points from Grade",
                    dialog = {
                        InformationDialog(
                            onDismiss = { it() },
                            text = "This is a description"
                        )
                    },
                    showExtraInformation = true
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnDoublePointsChange(value)) },
                    value = state.doublePointsState,
                    enabled = state.calculatePointsState,
                    name = "Double Calculated Points",
                    extraInfo = if (!state.calculatePointsState) "Enable 'Calculate Points' to enable setting" else "",
                    dialog = {
                        InformationDialog(
                            onDismiss = { it() },
                            text = "This is a description"
                        )
                    },
                    showExtraInformation = true
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.spacing.medium,
                            vertical = MaterialTheme.spacing.medium
                        )
                        .fillMaxWidth(),
                    value = state.minimumGrade,
                    singleLine = true,
                    isError = !state.validMinimumGrade,
                    label = {
                        Text(text = "Minimum Grade for Calculating Points")
                    },
                    supportingText = {
                        if (!state.validMinimumGrade) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.errorMessageMinimumGrade,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    onValueChange = { value -> onEvent(SettingsEvent.OnMinimumGradeChange(value))})
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)//, vertical = MaterialTheme.spacing.extraSmall)
                ) {
                    Text(
                        text = "Clear Data from the Device",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error,
                    ), onClick = { onEvent(SettingsEvent.OnDeleteDatabaseButtonClick) }) {
                        Text(text = "Clear Data")
                    }
                }
                Spacer(Modifier.height(MaterialTheme.spacing.large))
                Text(
                    "Backup",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.large),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.spacing.small,
                            bottom = MaterialTheme.spacing.large,
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium
                        )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = "Create Application Backup",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(0.6f)
                    )
                    Button(
                        modifier = Modifier.weight(0.4f),
                        onClick = { createBackup.launch("grader-backup-${LocalDate.now()}") }) {
                        Text(text = "Create Backup")
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = "Load Backup from File",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(0.6f)
                    )
                    Button(
                        modifier = Modifier.weight(0.4f),
                        onClick = { loadBackup.launch(arrayOf("application/json")) }) {
                        Text(text = "Load Backup")
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = "Create Application Report",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(0.6f)
                    )
                    Button(
                        modifier = Modifier.weight(0.4f),
                        onClick = { createReport.launch("report-${LocalDateTime.now()}.json") }) {
                        Text(text = "Create Report")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    GraderTheme {
        SettingsScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = SettingsState(appTheme = AppTheme.DEVICE_THEME, calculatePointsState = true),
            onEvent = {},
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenDarkModePreview() {
    GraderTheme {
        SettingsScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = SettingsState(appTheme = AppTheme.LIGHT_MODE),
            onEvent = {},
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}
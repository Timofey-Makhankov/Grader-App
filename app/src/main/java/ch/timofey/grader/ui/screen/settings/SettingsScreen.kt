package ch.timofey.grader.ui.screen.settings

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import ch.timofey.grader.R
import ch.timofey.grader.db.AppTheme
import ch.timofey.grader.navigation.NavigationDrawerItems
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.type.AppLanguage
import ch.timofey.grader.type.DateFormatting
import ch.timofey.grader.type.DeviceInfo
import ch.timofey.grader.ui.components.atom.DropDownMenu
import ch.timofey.grader.ui.components.molecules.InformationDialog
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.molecules.ShowNavigationIconsInformation
import ch.timofey.grader.ui.components.molecules.SwitchText
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale
import kotlin.time.Duration.Companion.seconds

@Composable
fun SettingsScreen(
    drawerState: DrawerState,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    scrollState: ScrollState = rememberScrollState(),
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    //val openDeleteDataDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
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
        Scaffold(modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() }, indication = null
        ) {
            focusManager.clearFocus()
        }, topBar = {
            AppBar(
                onNavigationIconClick = { scope.launch(Dispatchers.Main) { drawerState.open() } },
                actionIcon = Icons.Default.Menu,
                actionContentDescription = "Toggle Drawer",
                appBarTitle = "Settings"
            )
        }) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = scrollState)
                    .padding(it)
                    .fillMaxWidth(),
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
                    modifier = Modifier.padding(
                        top = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.large,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    )
                )
                DropDownMenu(
                    value = state.appTheme.title, title = "App Theme"
                ) { afterSelection ->
                    AppTheme.entries.filter { value -> value != state.appTheme }.forEach { theme ->
                        DropdownMenuItem(text = { Text(text = theme.title) }, onClick = {
                            onEvent(SettingsEvent.OnThemeChange(theme))
                            afterSelection()
                        })
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                DropDownMenu(
                    value = Locale(state.language.tag).displayLanguage, title = "language"
                ) { afterSelection ->
                    AppLanguage.entries.filter { value -> value != state.language }
                        .forEach { appLanguage ->
                            DropdownMenuItem(text = { Text(text = Locale(appLanguage.tag).displayLanguage) },
                                onClick = {
                                    onEvent(SettingsEvent.OnLanguageChange(appLanguage))
                                    afterSelection()
                                })
                        }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                DropDownMenu(
                    enable = false,
                    value = if (state.dateFormat != DateFormatting.DEFAULT) Locale(
                        state.dateFormat.language, state.dateFormat.country
                    ).displayName else "Follow System", title = "Date Format"
                ) { afterSelection ->
                    DateFormatting.entries.filter { value -> value != state.dateFormat }
                        .forEach { format ->
                            DropdownMenuItem(text = {
                                Text(
                                    text = Locale(
                                        format.language, format.country
                                    ).displayName
                                )
                            }, onClick = {
                                onEvent(SettingsEvent.OnDateFormatChange(format))
                                afterSelection()
                            })
                        }
                }
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value ->
                        onEvent(
                            SettingsEvent.OnShowNavigationIconsChange(
                                value
                            )
                        )
                    },
                    value = state.showNavigationIcons,
                    name = "Show Navigation Icons",
                    dialog = { InformationDialog(onDismiss = { it() }) { ShowNavigationIconsInformation() } },
                    showExtraInformation = true
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnSwapNavigationChange(value)) },
                    value = state.swapNavigation,
                    name = "Swap Long Press Navigation",
                    dialog = {
                        InformationDialog(onDismiss = { it() }) {
                            Text(text = "This is a description")
                        }
                    },
                    //enabled = false,
                    showExtraInformation = true
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnGradeColorChange(value)) },
                    value = state.colorGrades,
                    name = "Color Calculated Grades",
                    dialog = {
                        InformationDialog(onDismiss = { it() }) {
                            Text(text = "This is a description")
                        }
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
                    modifier = Modifier.padding(
                        top = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.large,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    )
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { _ -> },
                    value = false,
                    enabled = false,
                    name = "Round Grade to .5",
                    dialog = { InformationDialog(onDismiss = { it() }) {
                        InformationDialog(onDismiss = { it() }) {
                        Text(text = "This is a description")
                    } } },
                    showExtraInformation = true
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnCalculatePointsChange(value)) },
                    value = state.calculatePointsState,
                    name = "Calculate Points from Grade",
                    dialog = {
                        InformationDialog(onDismiss = { it() }) {
                            Text(text = "This is a description")
                        }
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
                        InformationDialog(onDismiss = { it() }) {
                            Text(text = "This is a description")
                        }
                    },
                    showExtraInformation = true
                )
                OutlinedTextField(modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.small
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Decimal
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    onValueChange = { value -> onEvent(SettingsEvent.OnMinimumGradeChange(value)) })
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
                        InformationDialog(onDismiss = { it() }) {
                            Text(text = "Enable Left Swipe to Delete a given on all List Screens. Upon deletion, it will show you a Snack bar of the deleted Item and can be undone")
                        }
                    },
                    showExtraInformation = true
                )
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
                    modifier = Modifier.padding(
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
                    Button(modifier = Modifier.weight(0.4f),
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
                    Button(modifier = Modifier.weight(0.4f),
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
                    Button(modifier = Modifier.weight(0.4f),
                        onClick = { createReport.launch("report-${LocalDateTime.now()}.json") }) {
                        Text(text = "Create Report")
                    }
                }
                if (state.showDeleteDataDialog) {
                    val buttonDisabled = remember { mutableStateOf(true) }
                    AlertDialog(
                        onDismissRequest = { onEvent(SettingsEvent.OnDismissDeleteData) },
                        confirmButton = {
                            TextButton(onClick = { onEvent(SettingsEvent.OnConfirmDeleteData) }, enabled = !buttonDisabled.value) {
                                Text(text = "Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { onEvent(SettingsEvent.OnDismissDeleteData) }) {
                                Text(text = "Cancel")
                            }
                        },
                        title = { Text(text = "Delete Application Data") },
                        icon = { Icon( imageVector = Icons.Default.DeleteForever, contentDescription = "Delete Application Data") },
                        text = { Text(text = stringResource(R.string.delete_data_dialog_content)) },
                        properties = DialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true
                        ),
                    )
                    LaunchedEffect(Unit) {
                        delay(5.seconds)
                        buttonDisabled.value = false
                    }
                }
            }
        }
    }
}
private class PreviewProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int> = listOf(0, Int.MAX_VALUE).asSequence()
}
@PreviewLightDark
@Composable
private fun SettingsScreenPreview(
    @PreviewParameter(PreviewProvider::class) scrollLocation: Int
) {
    GraderTheme(
        themeSetting = AppTheme.DEVICE_THEME
    ) {
        SettingsScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            state = SettingsState(appTheme = AppTheme.DEVICE_THEME, calculatePointsState = true),
            onEvent = {},
            scrollState = ScrollState(scrollLocation),
            uiEvent = emptyFlow(),
            onNavigate = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}
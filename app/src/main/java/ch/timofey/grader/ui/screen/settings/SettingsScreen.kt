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
import android.content.Context
import android.provider.OpenableColumns

fun getFileName(context: Context, uri: Uri): String? {
    var fileName: String? = null
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex != -1) {
                fileName = cursor.getString(displayNameIndex)
            }
        }
    }
    return fileName
}


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
            val filename = getFileName(context, uri)
            if (filename == null) {
                Log.e("SettingsScreen", "Could not retrieve filename from Uri.")
                return@rememberLauncherForActivityResult
            }

            context.contentResolver.openOutputStream(uri)?.use { file: OutputStream ->
                onEvent(SettingsEvent.OnCreateBackupFile(file, filename))
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
                actionContentDescription = stringResource(id = R.string.toggle_drawer),
                appBarTitle = stringResource(R.string.settings)
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
                    stringResource(R.string.app_styling),
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
                    value = state.appTheme.title, title = stringResource(R.string.app_theme)
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
                    value = Locale.forLanguageTag(state.language.tag).displayLanguage, title = stringResource(R.string.language)
                ) { afterSelection ->
                    AppLanguage.entries.filter { value -> value != state.language }
                        .forEach { appLanguage ->
                            DropdownMenuItem(text = { Text(text = Locale.forLanguageTag(appLanguage.tag).displayLanguage) },
                                onClick = {
                                    onEvent(SettingsEvent.OnLanguageChange(appLanguage))
                                    afterSelection()
                                })
                        }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                DropDownMenu(
                    enable = false,
                    value = if (state.dateFormat != DateFormatting.DEFAULT) Locale.Builder()
                        .setLanguage(state.dateFormat.language)    .setRegion(state.dateFormat.country)
                        .build().displayName else stringResource(R.string.follow_system),
                    title = stringResource(R.string.date_format)
                ) { afterSelection ->
                    DateFormatting.entries.filter { value -> value != state.dateFormat }
                        .forEach { format ->
                            DropdownMenuItem(text = {
                                Text(
                                    text = Locale.Builder()
                                        .setLanguage(format.language)            .setRegion(format.country)
                                        .build().displayName
                                )
                            },  onClick = {
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
                    name = stringResource(R.string.show_navigation_icons),
                    dialog = { InformationDialog(onDismiss = { it() }) { ShowNavigationIconsInformation() } },
                    showExtraInformation = true
                )
                SwitchText(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    onValueChange = { value -> onEvent(SettingsEvent.OnSwapNavigationChange(value)) },
                    value = state.swapNavigation,
                    name = stringResource(R.string.swap_long_press_navigation),
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
                    name = stringResource(R.string.color_calculated_grades),
                    dialog = {
                        InformationDialog(onDismiss = { it() }) {
                            Text(text = "This is a description")
                        }
                    },
                    showExtraInformation = true
                )
                Spacer(Modifier.height(MaterialTheme.spacing.large))
                Text(
                    stringResource(R.string.data_manipulation),
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
                    name = stringResource(R.string.calc_points_from_grade),
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
                    name = stringResource(R.string.double_calc_points),
                    extraInfo = if (!state.calculatePointsState) stringResource(R.string.extrainf_calc_double_points) else "",
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
                        Text(text = stringResource(R.string.min_grade_for_calc))
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
                    name = stringResource(R.string.enable_swipe_right_for_deletion),
                    dialog = {
                        InformationDialog(onDismiss = { it() }) {
                            Text(text = stringResource(R.string.enable_swipe_right_for_deletion_desc))
                        }
                    },
                    showExtraInformation = true
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)//, vertical = MaterialTheme.spacing.extraSmall)
                ) {
                    Text(
                        text = stringResource(R.string.clear_data_from_the_device),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error,
                    ), onClick = { onEvent(SettingsEvent.OnDeleteDatabaseButtonClick) }) {
                        Text(text = stringResource(R.string.clear_data))
                    }
                }
                Spacer(Modifier.height(MaterialTheme.spacing.large))
                Text(
                    stringResource(R.string.backup),
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
                        text = stringResource(R.string.create_application_backup),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(0.6f)
                    )
                    Button(modifier = Modifier.weight(0.4f),
                        onClick = { createBackup.launch("grader-backup-${LocalDate.now()}") }) {
                        Text(text = stringResource(R.string.create_backup))
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = stringResource(R.string.load_backup_from_file),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(0.6f)
                    )
                    Button(modifier = Modifier.weight(0.4f),
                        onClick = { loadBackup.launch(arrayOf("application/json")) }) {
                        Text(text = stringResource(R.string.load_backup))
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = stringResource(R.string.create_application_report),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(0.6f)
                    )
                    Button(modifier = Modifier.weight(0.4f),
                        onClick = { createReport.launch("report-${LocalDateTime.now()}.json") }) {
                        Text(text = stringResource(R.string.create_report))
                    }
                }
                if (state.showDeleteDataDialog) {
                    val buttonDisabled = remember { mutableStateOf(true) }
                    AlertDialog(
                        onDismissRequest = { onEvent(SettingsEvent.OnDismissDeleteData) },
                        confirmButton = {
                            TextButton(onClick = { onEvent(SettingsEvent.OnConfirmDeleteData) }, enabled = !buttonDisabled.value) {
                                Text(text = stringResource(R.string.confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { onEvent(SettingsEvent.OnDismissDeleteData) }) {
                                Text(text = stringResource(R.string.cancel))
                            }
                        },
                        title = { Text(text = stringResource(R.string.delete_application_data)) },
                        icon = { Icon( imageVector = Icons.Default.DeleteForever, contentDescription = stringResource(R.string.delete_application_data)) },
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
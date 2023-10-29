package ch.timofey.grader.ui.screen.calculator

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.components.atom.GradeInputField
import ch.timofey.grader.ui.components.molecules.NavigationDrawer
import ch.timofey.grader.ui.components.organisms.BottomAppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorScreen(
    state: CalculatorState,
    drawerState: DrawerState,
    onEvent: (CalculatorEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val scope = rememberCoroutineScope()
    NavigationDrawer(drawerState = drawerState,
        currentScreen = Screen.CalculatorScreen,
        items = NavigationDrawerItems.list,
        onItemClick = { menuItem ->
            println("Clicked on ${menuItem.title}")
            if (menuItem.onNavigate != Screen.CalculatorScreen.route) {
                onNavigate(UiEvent.Navigate(menuItem.onNavigate))
            }
            scope.launch {
                drawerState.close()
            }
        }) {
        Scaffold(
            topBar = {
                AppBar(
                    onNavigationIconClick = { scope.launch { drawerState.open() } },
                    actionIcon = Icons.Default.Menu,
                    actionContentDescription = "Toggle Drawer",
                    appBarTitle = "Calculator"
                )
            },
            bottomBar = {
                BottomAppBar(
                    text = String.format(
                        "Calculated Grade: %.2f",
                        state.calculatedGrade
                    )
                )
            }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(all = MaterialTheme.spacing.small)
            ) {
                items((0..<state.rowCount).toList(), key = { index -> index }) { index ->
                    GradeInputField(
                        modifier = Modifier.animateItemPlacement(),
                        weight = state.weights[index],
                        grade = state.grades[index],
                        onGradeChange = { grade ->
                            onEvent(CalculatorEvent.OnGradeChange(index, grade))
                        },
                        onWeightChange = { weight ->
                            onEvent(CalculatorEvent.OnWeightChange(index, weight))
                        }
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                }
                item(key = "Buttons") {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small)
                        .animateItemPlacement()) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = MaterialTheme.spacing.small),
                            colors = ButtonDefaults.buttonColors(),
                            onClick = { onEvent(CalculatorEvent.OnAddFieldClick) }) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Text Field")
                                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                                Text(text = "Add Field")
                            }
                        }
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = MaterialTheme.spacing.small),
                            colors = ButtonDefaults.buttonColors(),
                            onClick = { onEvent(CalculatorEvent.OnRemoveFieldClick) },
                            enabled = state.rowCount > 3
                        ) {
                            Row(modifier = Modifier) {
                                Icon(imageVector = Icons.Default.Remove, contentDescription = "Remove Text Field")
                                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                                Text(text = "Remove Field")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = false
)
@Composable
private fun CalculatorScreenPreview() {
    GraderTheme {
        CalculatorScreen(
            state = CalculatorState(
                grades = listOf("", "", ""),
                weights = listOf("1.0", "1.0", "1.0")
            ),
            onEvent = {},
            onNavigate = {},
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false
)
@Composable
private fun CalculatorScreenDarkModePreview() {
    GraderTheme {
        CalculatorScreen(
            state = CalculatorState(
                grades = listOf("", "", "", ""),
                weights = listOf("1.0", "1.0", "1.0", "1.0"),
                rowCount = 4
            ),
            onEvent = {},
            onNavigate = {},
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}

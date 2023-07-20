package ch.timofey.grader.ui.screen.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.GradeInputField
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.NavigationDrawerItems
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.launch

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
        Scaffold(topBar = {
            AppBar(
                onNavigationIconClick = { scope.launch { drawerState.open() } },
                icon = Icons.Default.Menu,
                contentDescription = "Toggle Drawer"
            )
        }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(all = MaterialTheme.spacing.small)
            ) {
                items(state.rowCount) { index ->
                    GradeInputField(onGradeChange = { value ->
                        onEvent(CalculatorEvent.OnGradeChange(index, value))
                    }, onWeightChange = { value ->
                        onEvent(CalculatorEvent.OnWeightChange(index, value))
                    })
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                }
                item {
                    Text(
                        text = "Grade: ${state.calculatedGrade}",
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = false
)
@Composable
private fun CalculatorScreenPreview() {
    GraderTheme {
        CalculatorScreen(state = CalculatorState(
            calculatedGrade = 6.0, rowCount = 3
        ),
            onEvent = {},
            onNavigate = {},
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
    showSystemUi = false
)
@Composable
private fun CalculatorScreenDarkModePreview() {
    GraderTheme {
        CalculatorScreen(state = CalculatorState(
            calculatedGrade = 4.75, rowCount = 5
        ),
            onEvent = {},
            onNavigate = {},
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}

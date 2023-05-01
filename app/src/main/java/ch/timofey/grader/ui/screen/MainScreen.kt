package ch.timofey.grader.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.timofey.grader.ui.components.Greeting
import ch.timofey.grader.ui.components.SchoolCard
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.FloatingActionButton
import ch.timofey.grader.ui.components.NavigationDrawer
import ch.timofey.grader.theme.GraderTheme
import ch.timofey.grader.ui.utils.MenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    drawerState: DrawerState
) {
    NavigationDrawer(
        drawerState = drawerState,
        items = listOf(
            MenuItem(
                id = "home",
                title = "Home",
                contentDescription = "Go to Home Screen",
                icon = Icons.Default.Home
            ),
            MenuItem(
                id = "share",
                title = "Share",
                contentDescription = "Go to Share Screen",
                icon = Icons.Default.Share
            ),
            MenuItem(
                id = "setting",
                title = "Settings",
                contentDescription = "Go to Settings Screen",
                icon = Icons.Default.Settings
            )
        ),
        onItemClick = {
            println("Clicked on ${it.title}")
        }
    ) {
        FloatingActionButton(
            onClick = { /*TODO*/ }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Greeting(name = "MainActivity")
                SchoolCard(
                    isChecked = true,
                    title = "Mathematics",
                    grade = 5.5
                )
                Button(
                    onClick = { navController.navigate(Screen.SecondScreen.route) },
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text(text = "Go to Other Activity")
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    GraderTheme {
        MainScreen(
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = false
)
@Composable
fun PreviewMainScreenDarkMode() {
    GraderTheme {
        MainScreen(navController = rememberNavController(), drawerState = rememberDrawerState(initialValue = DrawerValue.Open))
    }
}
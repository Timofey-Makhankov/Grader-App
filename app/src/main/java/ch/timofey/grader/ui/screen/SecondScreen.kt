package ch.timofey.grader.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.timofey.grader.ui.components.Greeting
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.components.FloatingActionButton

@Composable
fun SecondScreen(
    navController: NavController
) {
    FloatingActionButton(
        onClick = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting(name = "SecondActivity")
            Button(onClick = {
                navController.navigate(Screen.MainScreen.route)
            }) {
                Text(text = "Go to Main Activity")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSecondScreen() {
    SecondScreen(navController = rememberNavController())
}
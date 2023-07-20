package ch.timofey.grader.ui.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.GraderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    appBarTitle: String = ""
) {
    TopAppBar(title = {
        Text(text = appBarTitle)
    }, colors = TopAppBarDefaults.mediumTopAppBarColors(), navigationIcon = {
        IconButton(onClick = onNavigationIconClick) {
            Icon(imageVector = icon, contentDescription = contentDescription)
        }
    })
}

@Preview
@Composable
private fun AppBarPreview() {
    GraderTheme {
        AppBar(
            onNavigationIconClick = {},
            icon = Icons.Default.Menu,
            contentDescription = "Open Navigation Drawer",
            appBarTitle = "Schools"
        )
    }
}

@Preview(
    showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AppBarDarkModePreview() {
    GraderTheme {
        AppBar(
            onNavigationIconClick = {},
            icon = Icons.Default.Menu,
            contentDescription = "Open Navigation Drawer",
            appBarTitle = "Divisions"
        )
    }
}
package ch.timofey.grader.ui.components.organisms

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.components.molecules.ScreenIndicator
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    actionIcon: ImageVector,
    actionContentDescription: String,
    appBarTitle: String = "",
    locationIndicator: Boolean = false,
    pageIndex: Int? = 0,
    pageNumber: Int = 4
) {
    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = appBarTitle)
            if (locationIndicator && pageIndex != null) {
                Spacer(modifier = Modifier.weight(1f))
                ScreenIndicator(
                    modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                    pages = pageNumber,
                    index = pageIndex
                )
            }
        }
    },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = actionIcon, contentDescription = actionContentDescription)
            }
        })
}

@Preview
@Composable
private fun AppBarPreview() {
    GraderTheme {
        AppBar(
            onNavigationIconClick = {},
            actionIcon = Icons.Default.Menu,
            actionContentDescription = "Open Navigation Drawer",
            appBarTitle = "Schools",
            locationIndicator = true,
            pageIndex = 2
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
            actionIcon = Icons.Default.Menu,
            actionContentDescription = "Open Navigation Drawer",
            appBarTitle = "Divisions"
        )
    }
}
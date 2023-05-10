package ch.timofey.grader.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.mediumTopAppBarColors(),
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = icon, contentDescription = contentDescription)
            }
        }
    )
}
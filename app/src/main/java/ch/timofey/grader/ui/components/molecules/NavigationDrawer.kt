package ch.timofey.grader.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.MenuItem
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit,
    currentScreen: Screen,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = modifier.width(300.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Close Navigation Drawer",
                        modifier
                            .padding(MaterialTheme.spacing.medium)
                            .clickable {
                                scope.launch {
                                    drawerState.close()
                                }
                            })
                    Spacer(Modifier.width(MaterialTheme.spacing.small))
                    Text(
                        text = "Grader Navigation",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(Modifier.height(MaterialTheme.spacing.medium))
                items.forEach { item ->
                    NavigationDrawerItem(label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.contentDescription
                            )
                        },
                        selected = item.onNavigate == currentScreen.route,
                        onClick = { onItemClick(item) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
    ) {
        content()
    }
}
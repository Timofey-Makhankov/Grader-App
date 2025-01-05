package ch.timofey.grader.ui.components.organisms

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

@Immutable
data class FabAction (val action: () -> Unit, val icon: ImageVector)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExtendedActionButton (
    modifier: Modifier = Modifier,
    showMenu: Boolean = false,
    primaryAction: () -> Unit,
    primaryIcon: ImageVector,
    secondaryActions: List<FabAction> = emptyList()
) {
    Column(
        horizontalAlignment = Alignment.End,
    ) {
        for (secondaryAction in secondaryActions) {
            AnimatedVisibility(
                visible = showMenu,
            ) {
                SmallFloatingActionButton(
                    modifier = Modifier.then(modifier).padding(end = MaterialTheme.spacing.small),
                    onClick = secondaryAction.action
                ) {
                    Icon(imageVector = secondaryAction.icon, contentDescription = "")
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
        ExtendedFloatingActionButton (
            modifier = Modifier.then(modifier),
            onClick = primaryAction,
            expanded = showMenu,
            text = { Text("Add") },
            icon = { Icon(imageVector = primaryIcon, contentDescription = "") }
        )
    }
}

@Preview
@Composable
private fun ExtendedActionButtonClosedPreview() {
    GraderTheme {
        ExtendedActionButton(
            primaryAction = {},
            primaryIcon = Icons.Default.Add,
            secondaryActions = listOf(
                FabAction({}, Icons.Default.Draw),
                FabAction({}, Icons.Default.Remove)
            ),
            showMenu = false
        )
    }
}

@Preview
@Composable
private fun ExtendedActionButtonOpenPreview() {
    GraderTheme {
        ExtendedActionButton(
            primaryAction = {},
            primaryIcon = Icons.Default.Add,
            secondaryActions = listOf(
                FabAction({}, Icons.Default.Draw),
                FabAction({}, Icons.Default.Remove)
            ),
            showMenu = true
        )
    }
}
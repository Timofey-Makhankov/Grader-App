package ch.timofey.grader.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.timofey.grader.ui.components.atom.DismissDeleteBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeContainer(
    modifier: Modifier = Modifier,
    onSwipe: () -> Unit,
    swipeEnabled: Boolean = true,
    components: @Composable () -> Unit
) {
    if (swipeEnabled) {
        val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipe()
                    true
                }
                else -> false
            }
        }, positionalThreshold = { value -> (value / 8) })
        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                val isVisible = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = TweenSpec(durationMillis = 400)),
                    exit = fadeOut(animationSpec = TweenSpec(durationMillis = 400))
                ) {
                    DismissDeleteBackground(modifier, dismissState)
                }
            }
        ) {
            components()
        }
    } else{
        components()
    }
}
package ch.timofey.grader.ui.components.organisms.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.ui.components.atom.DismissDeleteBackground
import ch.timofey.grader.ui.components.molecules.cards.DivisionCard
import ch.timofey.grader.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionItem(
    modifier: Modifier = Modifier,
    onSwipe: (Division) -> Unit,
    onCheckBoxClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onLongClick: () -> Unit,
    disableSwipe: Boolean = false,
    division: Division
) {
    val currentItem by rememberUpdatedState(division)
    val card: @Composable () -> Unit = {
        DivisionCard(modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .then(modifier),
            division = division,
            onCheckBoxClick = onCheckBoxClick,
            onLongClick = onLongClick,
            onEditClick = onUpdateClick,
            onDeleteClick = onDeleteClick)
    }
    if (!disableSwipe) {
        val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipe(currentItem)
                }

                else -> Unit
            }
            true
        }, positionalThreshold = { value -> (value / 8) })
        SwipeToDismissBox(modifier = Modifier,
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            state = dismissState,
            backgroundContent = {
                val isVisible = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart
                AnimatedVisibility(
                    visible = isVisible, enter = fadeIn(
                        animationSpec = TweenSpec(
                            durationMillis = 400
                        )
                    ), exit = fadeOut(
                        animationSpec = TweenSpec(
                            durationMillis = 400
                        )
                    )
                ) { DismissDeleteBackground(dismissState) } }) { card() } }
    else { card() }

}
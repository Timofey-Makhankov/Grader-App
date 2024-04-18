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
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.ui.components.atom.DismissDeleteBackground
import ch.timofey.grader.ui.components.molecules.cards.SchoolCard
import ch.timofey.grader.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolItem(
    modifier: Modifier = Modifier,
    school: School,
    onSwipe: (School) -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
    onLongClick: () -> Unit,
    disableSwipe: Boolean = false,
    colorGrade: Boolean = false
) {
    val currentItem by rememberUpdatedState(school)
    val card: @Composable () -> Unit = {
        SchoolCard(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .then(modifier),
            school = currentItem,
            colorGrade = colorGrade,
            onCheckBoxClick = onCheckBoxClick,
            onLongClick = onLongClick,
            onDeleteClick = onDeleteClick,
            onEditClick = onUpdateClick
        )
    }
    if (!disableSwipe){
        val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipe(currentItem)
                    true
                }
                else -> false
            }
        }, positionalThreshold = { value -> (value / 8) })
        SwipeToDismissBox(state = dismissState,
            modifier = Modifier,
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                val isVisible = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = TweenSpec(durationMillis = 400)),
                    exit = fadeOut(animationSpec = TweenSpec(durationMillis = 400))
                ) {
                    DismissDeleteBackground(dismissState)
                }
            }){
            card()
        }
    } else {
        card()
    }
}

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.ui.components.atom.DismissDeleteBackground
import ch.timofey.grader.ui.components.molecules.cards.ExamCard
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamItem(
    modifier: Modifier = Modifier,
    exam: Exam,
    @Suppress("UNUSED_PARAMETER")
    points: Double? = null,
    onDeleteClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onSwipe: (Exam) -> Unit,
    onCheckBoxClick: () -> Unit,
) {
    val currentItem by rememberUpdatedState(exam)
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
        //directions = setOf(DismissDirection.EndToStart),
        enableDismissFromEndToStart = true,
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
            ) {
                DismissDeleteBackground(dismissState = dismissState)
            }
        }){
        ExamCard(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .then(modifier),
            exam = exam,
            onCheckBoxClick = onCheckBoxClick,
            onEditClick = onUpdateClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Preview
@Composable
private fun PreviewExamCard() {
    GraderTheme {
        ExamCard(exam = Exam(
            id = UUID.randomUUID(),
            weight = 1.0,
            name = "Exam 1",
            moduleId = UUID.randomUUID(),
            isSelected = false,
            date = LocalDate.now(),
            grade = 4.5,
            onDelete = false,
            description = LoremIpsum(15).values.joinToString()
        ), isOpen = true, onCheckBoxClick = {}, onDeleteClick = {}, onEditClick = {})
    }
}
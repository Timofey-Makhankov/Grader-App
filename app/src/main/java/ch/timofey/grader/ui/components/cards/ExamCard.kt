package ch.timofey.grader.ui.components.cards

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun ExamCard(
    modifier: Modifier = Modifier, exam: Exam, isOpen: Boolean = false, onCheckBoxClick: () -> Unit
) {
    val checkedState = remember { mutableStateOf(exam.isSelected) }
    val expanded = remember { mutableStateOf(isOpen) }
    val interactionSource = remember { MutableInteractionSource() }
    Card(modifier = Modifier
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300, easing = LinearOutSlowInEasing
            )
        )
        .clickable(interactionSource = interactionSource, indication = null) {
            expanded.value = !expanded.value
        }
        .then(modifier), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ), shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(0.9f),
                    text = exam.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(
                    modifier = Modifier.align(Alignment.Top),
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        onCheckBoxClick()
                    },
                    colors = CheckboxDefaults.colors()
                )
            }
            Text(
                modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
                text = exam.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expanded.value) 4 else 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AnimatedVisibility(
                visible = expanded.value
            ) {
                Column(modifier = Modifier.animateContentSize()) {
                    Text(
                        text = "Weight: ${exam.weight}"
                    )
                    Text(
                        text = "Exam taken: ${DateTimeFormatter.ISO_LOCAL_DATE.format(exam.date)}"
                    )
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Grade: ${exam.grade}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ExamCardPreview() {
    val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        ExamCard(exam = Exam(
            id = UUID.randomUUID(),
            name = f.educator.schoolName(),
            description = LoremIpsum(20).values.joinToString(""),
            grade = 1.0,
            weight = 1.0,
            date = LocalDate.now(),
            moduleId = UUID.randomUUID()
        ), onCheckBoxClick = {})
    }
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExamCardDarkModePreview() {
    val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        ExamCard(
            exam = Exam(
                id = UUID.randomUUID(),
                name = f.educator.schoolName(),
                description = LoremIpsum(20).values.joinToString(""),
                grade = 5.9,
                weight = 1.0,
                date = LocalDate.of(2023, 6, 20),
                moduleId = UUID.randomUUID()
            ), onCheckBoxClick = {}, isOpen = true
        )
    }
}
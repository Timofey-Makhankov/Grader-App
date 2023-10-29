package ch.timofey.grader.ui.components.molecules.cards

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModuleCard(
    modifier: Modifier = Modifier,
    module: Module,
    isOpen: Boolean = false,
    onCheckBoxClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val checkedState = remember { mutableStateOf(module.isSelected) }
    val expanded = remember { mutableStateOf(isOpen) }
    Card(modifier = Modifier
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300, easing = LinearOutSlowInEasing
            )
        )
        .combinedClickable(interactionSource = MutableInteractionSource(),
            indication = null,
            onClick = { expanded.value = !expanded.value },
            onLongClick = { onLongClick() })
        .then(modifier), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ), shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = module.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(
                    checked = checkedState.value, onCheckedChange = {
                        checkedState.value = it
                        onCheckBoxClick()
                    }, colors = CheckboxDefaults.colors()
                )
            }
            Text(
                modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
                text = module.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expanded.value) 4 else 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AnimatedVisibility(visible = expanded.value) {
                Column(modifier = Modifier.animateContentSize()) {
                    Text(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium),
                        text = "Teacher: ${module.teacherFirstname} ${module.teacherLastname}"
                    )
                }
            }
            if (module.grade != 0.0) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Average Grade: ${module.grade}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ModuleCardPreview() {
    val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        ModuleCard(
            module = Module(
                id = UUID.randomUUID(),
                name = f.science.branch.formalBasic(),
                description = LoremIpsum().values.joinToString(""),
                isSelected = false,
                divisionId = UUID.randomUUID(),
                teacherFirstname = "",
                teacherLastname = "",
            ),
            onCheckBoxClick = {},
            onLongClick = {},
        )
    }
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ModuleCardDarkModePreview() {
    val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        ModuleCard(
            module = Module(
                id = UUID.randomUUID(),
                name = f.science.branch.formalApplied(),
                description = LoremIpsum().values.joinToString(""),
                isSelected = true,
                divisionId = UUID.randomUUID(),
                teacherFirstname = f.name.firstName(),
                teacherLastname = f.name.lastName(),
                grade = 3.9
            ),
            isOpen = true,
            onCheckBoxClick = {},
            onLongClick = {},
        )
    }
}
package ch.timofey.grader.db.domain.division

import ch.timofey.grader.db.converter.UUIDSerializer
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class DivisionSerial(
    @Required
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Required
    @Serializable(with = UUIDSerializer::class)
    val schoolId: UUID,
    @Required
    val name: String,
    val description: String? = "",
    @Required
    @SerialName("school_year")
    val schoolYear: Int,
    val isSelected: Boolean = false,
    @Required
    val grade: Double,
    val onDelete: Boolean = false
)

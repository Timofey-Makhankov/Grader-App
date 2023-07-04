package ch.timofey.grader.db

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val language: Language = Language.ENGLISH
)

enum class Language(
    val id: String,
    val value: String
) {
    ENGLISH("en", "English"),
    GERMAN("de", "German"),
    RUSSIAN("ru", "Russian")
}
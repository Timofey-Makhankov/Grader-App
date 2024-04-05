package ch.timofey.grader.type

enum class DateFormatting(val locale: String, val title: String) {
    DEFAULT("default", "Follow System"),
    ENGLISH_US("en-US", "English US"),
    ENGLISH("en", "English"),
    GERMAN("de", "German"),
    RUSSIAN("ru", "Russian")
}
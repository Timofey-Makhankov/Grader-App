package ch.timofey.grader.type

enum class DateFormatting(val language: String, val country: String) {
    DEFAULT("default", ""),
    ENGLISH_US("en", "US"),
    ENGLISH("en", ""),
    GERMAN("de", ""),
    RUSSIAN("ru", "");

    companion object {
        fun getRegionTag(value: DateFormatting): String {
            return if (value.country != ""){
                "${value.language}-${value.country}"
            } else {
                value.language
            }
        }
    }
}
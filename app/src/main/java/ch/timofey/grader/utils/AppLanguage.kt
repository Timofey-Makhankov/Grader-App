package ch.timofey.grader.utils

enum class AppLanguage(val tag: String, val title: String){
    ENGLISH("en", "English"),
    GERMAN("de", "German"),
    RUSSIAN("ru", "Russian");

    companion object {
        fun getFromTag(tag: String): AppLanguage? {
            var v = tag
            if (tag.length > 2) {
                v = tag.slice(0..1)
            }
            return when (v){
                "en" -> ENGLISH
                "de" -> GERMAN
                "ru" -> RUSSIAN
                else -> null
            }
        }
        fun getFromTitle(tag: String): AppLanguage? {
            return when (tag){
                "English" -> ENGLISH
                "German" -> GERMAN
                "Russian" -> RUSSIAN
                else -> null
            }
        }
        fun getTitles(): List<String> {
            return AppLanguage.entries.map { value -> value.title }
        }
    }
}
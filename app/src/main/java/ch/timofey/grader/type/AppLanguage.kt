package ch.timofey.grader.type

enum class AppLanguage(val tag: String){
    ENGLISH("en"),
    GERMAN("de"),
    RUSSIAN("ru");

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
    }
}
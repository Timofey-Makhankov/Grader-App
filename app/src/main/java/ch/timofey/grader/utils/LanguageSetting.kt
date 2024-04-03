package ch.timofey.grader.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

fun getApplicationLanguage(): String {
    return AppCompatDelegate.getApplicationLocales().get(0)!!.toLanguageTag()
}

fun setApplicationLanguage(languageTag: String) {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))
}

package ch.timofey.grader

import ch.timofey.grader.db.AppTheme

data class MainState(
    val theme: AppTheme? = null
)

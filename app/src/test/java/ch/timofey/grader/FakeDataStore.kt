package ch.timofey.grader

import androidx.datastore.core.DataStore
import ch.timofey.grader.db.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataStore : DataStore<AppSettings> {
    override val data: Flow<AppSettings>
        get() = flowOf(
            AppSettings(
                calculatePoints = false,
                colorGrades = false,
                minimumGrade = 0.0
            )
        )

    override suspend fun updateData(transform: suspend (t: AppSettings) -> AppSettings): AppSettings {
        return AppSettings(
            calculatePoints = false,
            colorGrades = false,
            minimumGrade = 0.0
        )
    }
}
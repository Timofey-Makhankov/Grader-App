package ch.timofey.grader.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.AppSettingsSerializer
import ch.timofey.grader.db.AppDatabase
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.db.domain.division.DivisionRepositoryImpl
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.db.domain.exam.ExamRepositoryImpl
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.db.domain.module.ModuleRepositoryImpl
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.db.domain.school.SchoolRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val DATA_STORE_FILE_NAME = "app_settings.pb"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, "grader_database"
        ).allowMainThreadQueries()
            .build() // TODO I have to look into the allowMainThreadQueries function
    }

    @Provides
    @Singleton
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<AppSettings> {
        return DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            produceFile = { appContext.dataStoreFile(DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    @Provides
    @Singleton
    fun provideDivisionRepository(db: AppDatabase): DivisionRepository {
        return DivisionRepositoryImpl(db.divisionDao)
    }

    @Provides
    @Singleton
    fun provideExamRepository(db: AppDatabase): ExamRepository {
        return ExamRepositoryImpl(db.examDao)
    }

    @Provides
    @Singleton
    fun provideModuleRepository(db: AppDatabase): ModuleRepository {
        return ModuleRepositoryImpl(db.moduleDao)
    }

    @Provides
    @Singleton
    fun provideSchoolRepository(db: AppDatabase): SchoolRepository {
        return SchoolRepositoryImpl(db.schoolDao)
    }
}
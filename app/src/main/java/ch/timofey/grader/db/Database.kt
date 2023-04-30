package ch.timofey.grader.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ch.timofey.grader.db.domain.division.DivisionDao
import ch.timofey.grader.db.domain.exam.ExamDao
import ch.timofey.grader.db.domain.module.ModuleDao
import ch.timofey.grader.db.domain.school.SchoolDao
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.db.domain.school.School

@Database(entities = [Division::class, Exam::class, Module::class, School::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val divisionDao: DivisionDao
    abstract val examDao: ExamDao
    abstract val moduleDao: ModuleDao
    abstract val schoolDao: SchoolDao
}
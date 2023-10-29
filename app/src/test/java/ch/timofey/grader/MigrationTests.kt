package ch.timofey.grader

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import ch.timofey.grader.db.AppDatabase
import org.junit.Rule
import org.junit.Test
import java.io.IOException


//@RunWith(AndroidJUnit)
/*class MigrationTests {
    private val TEST_DB = "migration-test"

    //private val ALL_MIGRATIONS = arrayOf<Migration>(
    //    AutoMigration(2, 3),
    //    AutoMigration(3, 4)
    //)

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun testAllMigrations(){

    }
}*/
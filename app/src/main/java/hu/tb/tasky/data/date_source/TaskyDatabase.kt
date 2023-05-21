package hu.tb.tasky.data.date_source

import androidx.room.*
import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.model.TaskEntity

@Database(
    entities = [TaskEntity::class, ListEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class TaskyDatabase: RoomDatabase() {

    abstract val taskDao: TaskyDAO

    companion object {
        const val DATABASE_NAME = "tasky_db"
    }
}
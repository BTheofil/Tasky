package hu.tb.tasky.data.date_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.tb.tasky.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class TaskEntityDatabase: RoomDatabase() {

    abstract val taskDao: TaskEntityDAO

    companion object {
        const val DATABASE_NAME = "task_entity_db"
    }
}
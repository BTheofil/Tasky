package hu.tb.tasky.data.date_source

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.model.TaskEntity

@Database(
    entities = [TaskEntity::class, ListEntity::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = TaskEntityDatabase.Migration1To2::class),
        AutoMigration(from = 2, to = 3)
    ]
)
@TypeConverters(Converters::class)
abstract class TaskEntityDatabase: RoomDatabase() {

    abstract val taskDao: TaskEntityDAO

    @RenameColumn(tableName = "TaskEntity", fromColumnName = "id", toColumnName = "taskId")
    class Migration1To2: AutoMigrationSpec

    companion object {
        const val DATABASE_NAME = "task_entity_db"
    }
}
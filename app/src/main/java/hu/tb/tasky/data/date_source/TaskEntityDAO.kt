package hu.tb.tasky.data.date_source

import androidx.room.*
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskEntityDAO {

    @Query("SELECT * FROM TaskEntity")
    fun getTaskEntities(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isTaskDone = 1")
    fun getDoneTaskEntities(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isTaskDone = 0")
    fun getOngoingTaskEntities(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTaskEntityById(id: Int): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskEntity(taskEntity: TaskEntity): Long

    @Delete
    suspend fun deleteTaskEntity(taskEntity: TaskEntity)
}
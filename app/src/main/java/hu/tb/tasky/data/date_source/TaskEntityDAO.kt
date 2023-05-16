package hu.tb.tasky.data.date_source

import androidx.room.*
import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.model.relations.ListWithTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskEntityDAO {

    @Query("SELECT * FROM TaskEntity")
    fun getTaskEntities(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isTaskDone = 1")
    fun getDoneTaskEntities(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isTaskDone = 0")
    fun getOngoingTaskEntities(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE taskId = :id")
    suspend fun getTaskEntityById(id: Int): TaskEntity?

    @Transaction
    @Query("SELECT * FROM ListEntity")
    fun getListWithTask(): Flow<List<ListWithTask>>

    @Transaction
    @Query("SELECT * FROM ListEntity WHERE listId = :listId")
    suspend fun getSchoolWithStudents(listId: String): List<ListWithTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskEntity(taskEntity: TaskEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListEntity(listEntity: ListEntity): Long

    @Delete
    suspend fun deleteTaskEntity(taskEntity: TaskEntity)
}
package hu.tb.tasky.domain.repository

import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTaskEntities(): Flow<List<TaskEntity>>

    fun getDoneTaskEntities(): Flow<List<TaskEntity>>

    fun getOngoingTaskEntities(): Flow<List<TaskEntity>>

    suspend fun insertTaskEntity(taskEntity: TaskEntity): Long

    suspend fun getTaskEntityById(id: Int): TaskEntity?

    suspend fun deleteTask(task: TaskEntity)
}
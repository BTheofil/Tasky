package hu.tb.tasky.domain.repository

import hu.tb.tasky.domain.util.Sort
import hu.tb.tasky.domain.util.SortType
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskEntityRepository {

    fun getTaskEntities(): Flow<List<TaskEntity>>

    fun getDoneTaskEntities(sort: Sort = Sort.Date(SortType.Ascending)): Flow<List<TaskEntity>>

    fun getOngoingTaskEntities(sort: Sort = Sort.Date(SortType.Ascending)): Flow<List<TaskEntity>>

    suspend fun insertTaskEntity(taskEntity: TaskEntity): Long

    suspend fun getTaskEntityById(id: Int): TaskEntity?

    suspend fun deleteTask(task: TaskEntity)
}
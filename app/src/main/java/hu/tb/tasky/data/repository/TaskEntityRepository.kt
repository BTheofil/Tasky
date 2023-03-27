package hu.tb.tasky.data.repository

import hu.tb.tasky.data.date_source.TaskEntityDAO
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskEntityRepository(
    private val dao: TaskEntityDAO
) {

    fun getTaskEntities(): Flow<List<TaskEntity>> = dao.getTaskEntities()

    fun getDoneTaskEntities(): Flow<List<TaskEntity>> = dao.getDoneTaskEntities()

    suspend fun insertTaskEntity(taskEntity: TaskEntity): Long = dao.insertTaskEntity(taskEntity)

    suspend fun getTaskEntityById(id: Int) = dao.getTaskEntityById(id)

    suspend fun deleteTask(task: TaskEntity) = dao.deleteTaskEntity(task)
}
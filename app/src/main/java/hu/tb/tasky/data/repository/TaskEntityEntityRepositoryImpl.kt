package hu.tb.tasky.data.repository

import hu.tb.tasky.data.date_source.TaskEntityDAO
import hu.tb.tasky.domain.repository.TaskEntityRepository
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskEntityEntityRepositoryImpl(
    private val dao: TaskEntityDAO
) : TaskEntityRepository {

    override fun getTaskEntities(): Flow<List<TaskEntity>> = dao.getTaskEntities()

    override fun getDoneTaskEntities(): Flow<List<TaskEntity>> = dao.getDoneTaskEntities()

    override fun getOngoingTaskEntities(): Flow<List<TaskEntity>> = dao.getOngoingTaskEntities()

    override suspend fun insertTaskEntity(taskEntity: TaskEntity): Long = dao.insertTaskEntity(taskEntity)

    override suspend fun getTaskEntityById(id: Int): TaskEntity? = dao.getTaskEntityById(id)

    override suspend fun deleteTask(task: TaskEntity) = dao.deleteTaskEntity(task)
}
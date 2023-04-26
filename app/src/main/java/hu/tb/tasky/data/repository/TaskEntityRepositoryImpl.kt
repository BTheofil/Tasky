package hu.tb.tasky.data.repository

import hu.tb.tasky.data.date_source.TaskEntityDAO
import hu.tb.tasky.domain.util.Sort
import hu.tb.tasky.domain.repository.TaskEntityRepository
import hu.tb.tasky.domain.util.SortType
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskEntityRepositoryImpl(
    private val dao: TaskEntityDAO
) : TaskEntityRepository {

    override fun getTaskEntities(): Flow<List<TaskEntity>> = dao.getTaskEntities()

    override fun getDoneTaskEntities(sort: Sort): Flow<List<TaskEntity>> {
        return dao.getDoneTaskEntities().map { taskList ->
            sortLogic(taskList, sort)
        }
    }

    override fun getOngoingTaskEntities(sort: Sort): Flow<List<TaskEntity>> {
        return dao.getOngoingTaskEntities().map { taskList ->
            sortLogic(taskList, sort)
        }
    }

    override suspend fun insertTaskEntity(taskEntity: TaskEntity): Long =
        dao.insertTaskEntity(taskEntity)

    override suspend fun getTaskEntityById(id: Int): TaskEntity? = dao.getTaskEntityById(id)

    override suspend fun deleteTask(task: TaskEntity) = dao.deleteTaskEntity(task)

    private fun sortLogic(taskList: List<TaskEntity>, sort: Sort): List<TaskEntity> {
        when (sort.type) {
            is SortType.Ascending -> {
                return when (sort) {
                    is Sort.Title -> {
                        taskList.sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }
                        )
                    }
                    is Sort.Date -> {
                        taskList.sortedBy { it.id }
                    }
                }
            }
            is SortType.Descending -> {
                return when (sort) {
                    is Sort.Title -> {
                        taskList.sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }
                        ).reversed()
                    }
                    is Sort.Date -> {
                        taskList.sortedByDescending { it.id }
                    }
                }
            }
        }
    }
}
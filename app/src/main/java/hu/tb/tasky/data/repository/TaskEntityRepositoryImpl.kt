package hu.tb.tasky.data.repository

import hu.tb.tasky.data.date_source.TaskEntityDAO
import hu.tb.tasky.domain.repository.TaskEntityRepository
import hu.tb.tasky.domain.util.Order
import hu.tb.tasky.domain.util.OrderType
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskEntityRepositoryImpl(
    private val dao: TaskEntityDAO
) : TaskEntityRepository {

    override fun getTaskEntities(): Flow<List<TaskEntity>> = dao.getTaskEntities()

    override fun getDoneTaskEntities(order: Order, orderType: OrderType): Flow<List<TaskEntity>> {
        return dao.getDoneTaskEntities().map { taskList ->
            sortLogic(taskList, order, orderType)
        }
    }

    override fun getOngoingTaskEntities(order: Order, orderType: OrderType): Flow<List<TaskEntity>> {
        return dao.getOngoingTaskEntities().map { taskList ->
            sortLogic(taskList, order, orderType)
        }
    }

    override suspend fun insertTaskEntity(taskEntity: TaskEntity): Long =
        dao.insertTaskEntity(taskEntity)

    override suspend fun getTaskEntityById(id: Int): TaskEntity? = dao.getTaskEntityById(id)

    override suspend fun deleteTask(task: TaskEntity) = dao.deleteTaskEntity(task)

    private fun sortLogic(taskList: List<TaskEntity>, order: Order, orderType: OrderType): List<TaskEntity> {
        when (orderType) {
            OrderType.ASCENDING -> {
                return when (order) {
                    Order.TITLE -> {
                        taskList.sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }
                        )
                    }
                    Order.TIME -> {
                        taskList.sortedBy { it.id }
                    }
                }
            }
            OrderType.DESCENDING -> {
                return when (order) {
                    Order.TITLE -> {
                        taskList.sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }
                        ).reversed()
                    }
                    Order.TIME -> {
                        taskList.sortedByDescending { it.id }
                    }
                }
            }
        }
    }
}
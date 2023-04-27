package hu.tb.tasky.domain.repository

import hu.tb.tasky.domain.util.Order
import hu.tb.tasky.domain.util.OrderType
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskEntityRepository {

    fun getTaskEntities(): Flow<List<TaskEntity>>

    fun getDoneTaskEntities(order: Order, orderType: OrderType): Flow<List<TaskEntity>>

    fun getOngoingTaskEntities(order: Order, orderType: OrderType): Flow<List<TaskEntity>>

    suspend fun insertTaskEntity(taskEntity: TaskEntity): Long

    suspend fun getTaskEntityById(id: Int): TaskEntity?

    suspend fun deleteTask(task: TaskEntity)
}
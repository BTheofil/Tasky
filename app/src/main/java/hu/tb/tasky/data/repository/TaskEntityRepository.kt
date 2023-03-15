package hu.tb.tasky.data.repository

import android.util.Log
import hu.tb.tasky.data.date_source.TaskEntityDAO
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class TaskEntityRepository(
    private val dao: TaskEntityDAO
) {

    fun getTaskEntities(): Flow<List<TaskEntity>> {
        dao.getTaskEntities().onEach {
            Log.d("MYTAG", "REPO" + it)
        }
        return dao.getTaskEntities()
    }

    suspend fun insertTaskEntity(taskEntity: TaskEntity): Long {
        return dao.insertTaskEntity(taskEntity)
    }
}
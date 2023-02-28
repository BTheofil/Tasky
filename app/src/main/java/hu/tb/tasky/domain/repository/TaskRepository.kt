package hu.tb.tasky.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import hu.tb.tasky.model.Task

interface TaskRepository {

    fun getTaskList (): SnapshotStateList<Task>

    fun addTask(item: Task)
}
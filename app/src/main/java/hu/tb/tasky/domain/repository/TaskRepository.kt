package hu.tb.tasky.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import hu.tb.tasky.model.Task

interface TaskRepository {

    fun getTaskList(): SnapshotStateList<Task>

    fun getTaskById(title: String): Task?

    fun addTask(item: Task)
}
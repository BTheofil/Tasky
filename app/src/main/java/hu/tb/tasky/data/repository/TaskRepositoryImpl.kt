package hu.tb.tasky.data.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import hu.tb.tasky.data.date_source.MockTask
import hu.tb.tasky.domain.repository.TaskRepository
import hu.tb.tasky.model.Task
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val db: MockTask
): TaskRepository {

    override fun getTaskList(): SnapshotStateList<Task> {
        return db.get()
    }

    override fun addTask(item: Task) {
        return db.add(item)
    }
}
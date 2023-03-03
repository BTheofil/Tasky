package hu.tb.tasky.data.date_source

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import hu.tb.tasky.model.Task

class MockTask {
    private val list = emptyList<Task>().toMutableStateList()

    fun add(item: Task) {
        list.add(item)
    }

    fun get(): SnapshotStateList<Task> {
        return list
    }
}
package hu.tb.tasky.domain.repository

import hu.tb.tasky.model.Task

interface TaskRepository {

    fun getTaskList (): ArrayList<Task>

    fun addTask(item: Task)
}
package hu.tb.tasky.data.date_source

import hu.tb.tasky.model.Task

class MockTask {

    private val list = ArrayList<Task>()

    fun add(item: Task) {
        list.add(item)
    }

    fun get(): ArrayList<Task> {
        return list
    }
}
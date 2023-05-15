package hu.tb.tasky.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.model.TaskEntity

data class ListWithTask(
    @Embedded val list: ListEntity,
    @Relation(
        parentColumn = "listId",
        entityColumn = "taskId"
    )
    val listOfTask: List<TaskEntity>
)

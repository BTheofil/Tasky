package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.tasky.model.Task

@Composable
fun TaskItemContainer(taskItem: Task, onCheckedChange: (Boolean) -> Unit) {

    var formattedDate: String? = null
    if (taskItem.expireDate != null && taskItem.expireTime != null) {
        formattedDate = " ${
            if (taskItem.expireDate.monthValue < 10) {
                "0" + taskItem.expireDate.monthValue
            } else {
                taskItem.expireDate.monthValue
            }
        }. ${taskItem.expireDate.dayOfMonth}. ${taskItem.expireTime.hour}:${taskItem.expireTime.minute}"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        Row(Modifier.padding(14.dp)) {
            Column(
                Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = taskItem.title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                )
                Divider(Modifier.padding(top = 4.dp, bottom = 4.dp))
                Text(
                    text = taskItem.description,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )
            }
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    checked = taskItem.isDone,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.padding(0.dp)
                )
                if (formattedDate != null) {
                    Text(text = "Expire: $formattedDate")
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskItemContainerPreview() {
    val testTask = Task(
        title = "Test",
        description = "Something more about the task...",
        expireDate = null,
        expireTime = null,
        isDone = false,
    )
    TaskItemContainer(taskItem = testTask, onCheckedChange = {})
}
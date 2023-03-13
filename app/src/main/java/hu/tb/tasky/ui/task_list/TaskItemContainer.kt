package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.tasky.model.Task
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun TaskItemContainer(
    modifier: Modifier = Modifier,
    taskItem: Task,
) {

    var isDone by rememberSaveable { mutableStateOf(false) }

    val formattedDate = taskItem.expireDate?.let { date ->
        taskItem.expireTime?.let { time ->
            "${date.monthValue.toString().padStart(2, '0')}. " +
                    "${date.dayOfMonth.toString().padStart(2, '0')}. " +
                    "${time.hour.toString().padStart(2, '0')}:${
                        time.minute.toString().padStart(2, '0')
                    }"
        }
    }

    Card(
        modifier = modifier
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
                    checked = isDone,
                    onCheckedChange = { isDone = it },
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
        initialChecked = false,
    )
    TaskItemContainer(taskItem = testTask)
}
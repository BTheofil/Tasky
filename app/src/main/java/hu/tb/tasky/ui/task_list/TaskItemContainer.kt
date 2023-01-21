package hu.tb.tasky.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.tasky.model.Task
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskItemContainer(taskItem: Task, onCheckedChange: (Boolean) -> Unit) {

    //todo give solution about time
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val output: String = formatter.format(parser.parse(taskItem.expireDate))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        Row(Modifier.padding(16.dp)) {
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
                Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
            ) {
                Checkbox(checked = taskItem.isDone, onCheckedChange = onCheckedChange)
                Text(
                    text = output,
                )
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
        expireDate = "",
        isDone = false,
    )
    val isChecked = remember { mutableStateOf(false) }
    TaskItemContainer(taskItem = testTask, onCheckedChange = {})
}
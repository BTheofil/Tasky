package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.tasky.R
import hu.tb.tasky.model.TaskEntity

@Composable
fun TaskItemContainer(
    modifier: Modifier = Modifier,
    taskItem: TaskEntity,
    isDone: Boolean,
    onDoneClick: (Boolean) -> Unit,
) {
    val formattedDate = taskItem.expireDate?.let { date ->
        taskItem.expireTime?.let { time ->
            "${date.monthValue.toString().padStart(2, '0')}-" +
                    "${date.dayOfMonth.toString().padStart(2, '0')} " +
                    "${time.hour.toString().padStart(2, '0')}:${
                        time.minute.toString().padStart(2, '0')
                    }"
        }
    }

    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = onDoneClick,
            )
        }
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = taskItem.title,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            if (taskItem.description != "") {
                Text(
                    text = taskItem.description,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Row() {
                if (formattedDate != null) {
                    Icon(
                        painter = painterResource(R.drawable.outline_alarm_24),
                        contentDescription = "Expire icon",
                    )
                    Text(
                        text = "$formattedDate"
                    )
                }
            }
        }
    }
    Divider()
}

@Preview
@Composable
fun TaskItemContainerPreview() {
    val testTask = TaskEntity(
        title = "Test",
        description = "Something more about the task...",
        expireDate = null,
        expireTime = null,
        isTaskDone = false,
    )
    TaskItemContainer(taskItem = testTask, onDoneClick = {}, isDone = false)
}
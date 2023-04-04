package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
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

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (Checkbox,
            ContentInfo,
            TaskExpireDateTime,
            AlarmIcon,
            Divider
        ) = createRefs()
        Checkbox(
            modifier = Modifier.constrainAs(Checkbox) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(Divider.top)
            },
            checked = isDone,
            onCheckedChange = onDoneClick,
        )
        Column(modifier = Modifier
            .constrainAs(ContentInfo) {
                start.linkTo(Checkbox.end)
                top.linkTo(parent.top)
                bottom.linkTo(Divider.bottom)
            },
        ) {
            Text(
                text = taskItem.title,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
            )
            if (taskItem.description != "") {
                Text(
                    text = taskItem.description,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                )
            }
        }
        if (formattedDate != null) {
            Icon(
                modifier = Modifier.constrainAs(AlarmIcon) {
                    end.linkTo(TaskExpireDateTime.start)
                    bottom.linkTo(Divider.top)
                },
                painter = painterResource(R.drawable.outline_alarm_24),
                contentDescription = "Expire icon",
            )
            Text(
                modifier = Modifier.constrainAs(TaskExpireDateTime) {
                    end.linkTo(parent.end)
                    bottom.linkTo(Divider.top)
                },
                text = "$formattedDate"
            )
        }
        Divider(
            Modifier
                .constrainAs(Divider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
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
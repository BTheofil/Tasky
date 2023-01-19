package hu.tb.tasky.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.tasky.model.Task
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItemContainer(taskItem: Task) {
    Card(
        onClick = { /* Do something */ },
        modifier = Modifier.size(width = 180.dp, height = 100.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Text("Clickable", Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
fun TaskItemContainerPreview(){
    val testTask = Task(
        title = "Test",
        description = "Something more about the task...",
        expireDate = Calendar.getInstance(),
        isDone = false,
    )
    TaskItemContainer(taskItem = testTask)
}
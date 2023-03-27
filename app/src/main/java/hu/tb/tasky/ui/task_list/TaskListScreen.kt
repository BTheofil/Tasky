package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.ui.components.FloatingActionButtonComponent
import hu.tb.tasky.ui.components.TopBar
import hu.tb.tasky.ui.route.RouteNames

@Composable
fun TaskListScreen(
    taskListViewModel: TaskListViewModel = hiltViewModel(),
    navController: NavController
) {
    val items: List<TaskEntity> by taskListViewModel.allTaskList.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = { FloatingActionButtonComponent(navController = navController) }
    ) { contentPadding ->
        Column(modifier = Modifier
            .padding(top = contentPadding.calculateTopPadding() + 16.dp)
            .padding(
                horizontal = 16.dp,
            )) {
            Button(
                onClick = {
                    taskListViewModel.onEvent(TaskListEvent.ShowDoneTask)
                }) {
                Text(text = "Show done tasks")
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(
                        horizontal = 16.dp,
                    )
            ) {
                items(
                    items = items,
                    key = { task -> task.id!! }
                ) { task ->
                    TaskItemContainer(
                        modifier = Modifier
                            .clip(CardDefaults.shape)
                            .clickable {
                                navController.navigate(RouteNames.ADD_EDIT_SCREEN + "?editedTask=${task.id}")
                            },
                        taskItem = task,
                        onDeleteTask = { taskListViewModel.onEvent(TaskListEvent.OnDeleteClick(task)) },
                        isDone = task.initialChecked ,
                        onDoneClick = { taskListViewModel.onEvent(TaskListEvent.OnDoneClick(task, it)) }
                    )
                }
            }
        }
    }
}
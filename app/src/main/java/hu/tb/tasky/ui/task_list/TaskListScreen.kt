package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.tb.tasky.ui.TaskItemContainer

@Composable
fun TaskListScreen(
    contentPadding: PaddingValues,
    taskListViewModel: TaskListViewModel = viewModel(),
){
    LazyColumn(Modifier
        .padding(top = contentPadding.calculateTopPadding() + 16.dp)
        .padding(horizontal = 16.dp
        )
    ){
        items(taskListViewModel.taskList.size){ index ->
            TaskItemContainer(
                taskListViewModel.taskList[index],
                onCheckedChange = { taskListViewModel.onTaskIsDoneChange(index, it) }
            )
        }
    }

}
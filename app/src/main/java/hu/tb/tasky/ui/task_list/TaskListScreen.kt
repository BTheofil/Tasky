package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    taskListViewModel: TaskListViewModel = hiltViewModel(),
    navController: NavController
) {
    val items: List<TaskEntity> by taskListViewModel.saveState.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = { FloatingActionButtonComponent(navController = navController) }
    ) { contentPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding() + 16.dp)
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
                    onDeleteTask = { taskListViewModel.onEvent(TaskListEvent.OnDeleteClick(task)) }
                )
            }
        }
    }
}
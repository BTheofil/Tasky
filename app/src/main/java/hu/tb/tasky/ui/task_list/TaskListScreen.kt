package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.tb.tasky.ui.main.FloatingActionButtonComponent
import hu.tb.tasky.ui.main.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    taskListViewModel: TaskListViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = { FloatingActionButtonComponent(navController = navController) }
    ) { contentPadding ->
        LazyColumn(
            Modifier
                .padding(top = contentPadding.calculateTopPadding() + 16.dp)
                .padding(
                    horizontal = 16.dp,
                )
        ) {
            items(
                items = taskListViewModel.taskList,
                key = { task -> task.title }
            ) { task ->
                TaskItemContainer(
                    task,
                )
            }
        }
    }
}
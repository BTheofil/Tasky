package hu.tb.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.tb.tasky.ui.add_edit_task.AddEditTaskScreen
import hu.tb.tasky.ui.route.RouteNames.ADD_EDIT_SCREEN
import hu.tb.tasky.ui.route.RouteNames.MAIN_SCREEN
import hu.tb.tasky.ui.task_list.TaskListScreen
import hu.tb.tasky.ui.task_list.TaskListViewModel
import hu.tb.tasky.ui.theme.TaskyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            TaskyTheme {
                NavHost(
                    navController = navController,
                    startDestination = MAIN_SCREEN
                ) {
                    composable(MAIN_SCREEN) {
                        val viewModel = hiltViewModel<TaskListViewModel>()
                        viewModel.update()
                        val state by viewModel.state.collectAsState()
                        TaskListScreen(
                            state,
                            navController,
                            viewModel::onEvent,
                            viewModel.dataStore,
                        )
                    }
                    composable(
                        route = ADD_EDIT_SCREEN + "?editedTask={editedTask}",
                        arguments = listOf(navArgument(name = "editedTask") {
                            defaultValue = -1
                            type = NavType.IntType
                        })
                    ) {
                        AddEditTaskScreen(
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}
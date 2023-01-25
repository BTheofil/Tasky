package hu.tb.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import hu.tb.tasky.ui.add_edit_task.AddEditTaskScreen
import hu.tb.tasky.ui.route.RouteNames.ADD_EDIT_SCREEN
import hu.tb.tasky.ui.route.RouteNames.MAIN_SCREEN
import hu.tb.tasky.ui.task_list.TaskListScreen
import hu.tb.tasky.ui.theme.TaskyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)

        setContent {
            val navController = rememberNavController()
            TaskyTheme {
                NavHost(
                    navController = navController,
                    startDestination = MAIN_SCREEN
                ) {
                    composable(MAIN_SCREEN) { TaskListScreen(navController = navController) }
                    composable(ADD_EDIT_SCREEN) { AddEditTaskScreen(navController = navController, null) }
                }
            }
        }
    }
}
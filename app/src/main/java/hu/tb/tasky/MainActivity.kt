package hu.tb.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.tasky.model.Task
import hu.tb.tasky.ui.TaskItemContainer
import hu.tb.tasky.ui.theme.TaskyTheme
import java.util.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskList = listOf<Task>(Task(
            title = "Test",
            description = "Something more about the task...",
            expireDate = Calendar.getInstance(),
            isDone = false,
        ),)

        setContent {
            TaskyTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    "Centered TopAppBar",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {},
                        ){
                            Icon(Icons.Filled.Add, "Add new task")
                        }
                    }
                ) { contentPadding ->
                    LazyColumn(Modifier.padding(top = contentPadding.calculateTopPadding())){
                        items(taskList.size){ index ->
                            TaskItemContainer(taskList[index])
                        }
                    }
                    Box(modifier = Modifier.padding(top = contentPadding.calculateTopPadding())) {
                        Text(text = "Hello")
                    }
                }
            }
        }
    }
}
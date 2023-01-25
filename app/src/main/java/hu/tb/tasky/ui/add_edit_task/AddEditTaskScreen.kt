package hu.tb.tasky.ui.add_edit_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hu.tb.tasky.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    taskItem: Task?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if(taskItem == null){
                        Text(text = "Create New Task")
                    } else {
                        Text(text = "Edit Task")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                },
            )
        },
    ) { contentPadding ->
        Column(modifier = Modifier.padding(top = contentPadding.calculateTopPadding())) {

        }
    }
}
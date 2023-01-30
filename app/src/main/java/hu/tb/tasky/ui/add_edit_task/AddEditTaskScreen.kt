package hu.tb.tasky.ui.add_edit_task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.tb.tasky.R
import hu.tb.tasky.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    taskItem: Task?,
    viewModel: AddEditTaskViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (taskItem == null) {
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
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column() {
                Box(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.Black),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        BasicTextField(
                            value = "",
                            onValueChange = {},
                            //todo hint
                        )
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Color.Black
                        )
                        BasicTextField(
                            value = "",
                            onValueChange = {},
                            //todo hint
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_schedule_24),
                        contentDescription = "Expire icon"
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                    Text(text = "Task expire")
                    //todo date picker
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    onClick = {}
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {}
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Preview
@Composable
fun AddEditTaskScreenPreview() {
    AddEditTaskScreen(navController = rememberNavController(), taskItem = null)
}
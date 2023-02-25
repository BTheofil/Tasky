package hu.tb.tasky.ui.add_edit_task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.tb.tasky.R
import hu.tb.tasky.model.Task
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import android.app.TimePickerDialog
import android.widget.TimePicker
import android.app.DatePickerDialog
import android.widget.DatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController, taskItem: Task?, viewModel: AddEditTaskViewModel = viewModel()
) {
    Scaffold(
        topBar = { TopBar(taskItem, navController) },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AddEditForm(
                TitleValue = taskItem?.title ?: viewModel.task.value.title,
                OnTitleChange = { viewModel.onEvent(AddEditTaskEvent.OnTitleChange(it)) },
                DescriptionValue = taskItem?.description ?: viewModel.task.value.description,
                OnDescriptionChange = { viewModel.onEvent(AddEditTaskEvent.OnDescriptionChange(it)) },
                DateValue = taskItem?.expireDate ?: viewModel.task.value.expireDate,
                OnDateChange = { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                    viewModel.onEvent(AddEditTaskEvent.OnDateChange(selectedDate))
                },
                TimeValue = taskItem?.expireTime ?: viewModel.task.value.expireTime,
                OnTimeChange = { _, hourOfDay, minute ->
                    val selectedTime = LocalTime.of(hourOfDay, minute)
                    viewModel.onEvent(AddEditTaskEvent.OnTimeChange(selectedTime))
                },
            )
            Buttons(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(taskItem: Task?, navController: NavController) {
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
                    imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Arrow"
                )
            }
        },
    )
}

@Composable
fun AddEditForm(
    TitleValue: String,
    OnTitleChange: (String) -> Unit,
    DescriptionValue: String,
    OnDescriptionChange: (String) -> Unit,
    DateValue: LocalDate?,
    OnDateChange: (DatePicker, Int, Int, Int) -> Unit,
    TimeValue: LocalTime?,
    OnTimeChange: (TimePicker, Int, Int) -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(16.dp)
                )
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                BasicTextField(value = TitleValue,
                    onValueChange = OnTitleChange,
                    decorationBox = { innerTextField ->
                        if (TitleValue.isEmpty()) {
                            Text(
                                text = "Title", color = Color.LightGray
                            )
                        }
                        innerTextField()
                    })
                Divider(
                    modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black
                )
                BasicTextField(value = DescriptionValue,
                    onValueChange = OnDescriptionChange,
                    decorationBox = { innerTextField ->
                        if (DescriptionValue.isEmpty()) {
                            Text(
                                text = "Description", color = Color.LightGray
                            )
                        }
                        innerTextField()
                    })
            }
        }
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_schedule_24),
                contentDescription = "Expire icon",
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Expire time:")
            Spacer(modifier = Modifier.width(8.dp))
            val datePickerDialog = DatePickerDialog(
                LocalContext.current,
                OnDateChange,
                LocalDate.now().year,
                LocalDate.now().month.value,
                LocalDate.now().dayOfMonth,
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        datePickerDialog.show()
                    }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = DateValue?.toString() ?: "Select Date")
            }
            Spacer(modifier = Modifier.width(8.dp))
            val timePickerDialog = TimePickerDialog(
                LocalContext.current,
                OnTimeChange, LocalTime.now().hour, LocalTime.now().minute, false
            )
            Box(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        timePickerDialog.show()
                    }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = TimeValue?.toString() ?: "Select Time")
            }
        }
    }
}

@Composable
fun Buttons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        OutlinedButton(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Cancel")
        }
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Save")
        }
    }
}

@Preview
@Composable
fun AddEditTaskScreenPreview() {
    AddEditTaskScreen(navController = rememberNavController(), taskItem = null)
}
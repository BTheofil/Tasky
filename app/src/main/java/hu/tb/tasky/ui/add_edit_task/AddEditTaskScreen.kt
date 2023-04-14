package hu.tb.tasky.ui.add_edit_task

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.tb.tasky.R
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {},
    )
    SideEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopBar(
                viewModel.task.value.id,
                navController
            ) { viewModel.onEvent(AddEditTaskEvent.OnDeleteClick) }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    keyboardController?.hide()
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AddEditForm(
                TitleValue = viewModel.task.value.title,
                OnTitleChange = { viewModel.onEvent(AddEditTaskEvent.OnTitleChange(it)) },
                DescriptionValue = viewModel.task.value.description,
                OnDescriptionChange = { viewModel.onEvent(AddEditTaskEvent.OnDescriptionChange(it)) },
                DateValue = viewModel.task.value.expireDate,
                OnDateChange = { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                    viewModel.onEvent(AddEditTaskEvent.OnDateChange(selectedDate))
                },
                TimeValue = viewModel.task.value.expireTime,
                OnTimeChange = { _, hourOfDay, minute ->
                    val selectedTime = LocalTime.of(hourOfDay, minute)
                    viewModel.onEvent(AddEditTaskEvent.OnTimeChange(selectedTime))
                },
                viewModel.task.value.isTitleError
            )
            Buttons(
                OnSaveClicked = {
                    viewModel.Save {
                        if (it) {
                            navController.popBackStack()
                        }
                    }

                },
                OnCancelClicked = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    taskId: Int?,
    navController: NavController,
    OnDeleteClick: () -> Unit,
) {
    TopAppBar(
        title = {
            if (taskId == null) {
                Text(text = stringResource(id = R.string.create_new_task))
            } else {
                Text(text = stringResource(id = R.string.edit_task))
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Arrow"
                )
            }
        },
        actions = {
            if (taskId != null) {
                IconButton(onClick = {
                    OnDeleteClick()
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_delete_outline_24),
                        contentDescription = "Delete task"
                    )
                }
            }
        }
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
    TitleErrorState: Boolean,
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BasicTextField(
                        modifier = Modifier.weight(0.8f),
                        value = TitleValue,
                        onValueChange = OnTitleChange,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                        maxLines = 1,
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (TitleValue.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.title), color = Color.LightGray,
                                )
                            }
                            innerTextField()
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    )
                    AnimatedVisibility(visible = TitleErrorState) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_error_outline_24),
                            contentDescription = "Error icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp, color = Color.Black
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxSize(),
                    value = DescriptionValue,
                    onValueChange = OnDescriptionChange,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    decorationBox = { innerTextField ->
                        if (DescriptionValue.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.description_optional), color = Color.LightGray
                            )
                        }
                        innerTextField()
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
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
            Text(text = stringResource(id = R.string.expire_time))
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
                    .width(100.dp)
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
                Text(
                    text = DateValue?.toString() ?: stringResource(id = R.string.select_date),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
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
                Text(
                    text = TimeValue?.toString() ?: stringResource(id = R.string.select_time),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,

                )
            }
        }
    }
}

@Composable
fun Buttons(
    OnSaveClicked: () -> Unit,
    OnCancelClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        OutlinedButton(
            onClick = OnCancelClicked
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }
        Button(
            onClick = OnSaveClicked
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Preview
@Composable
fun AddEditTaskScreenPreview() {
    AddEditTaskScreen(navController = rememberNavController())
}
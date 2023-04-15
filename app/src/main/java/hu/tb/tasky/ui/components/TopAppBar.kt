package hu.tb.tasky.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import hu.tb.tasky.R

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
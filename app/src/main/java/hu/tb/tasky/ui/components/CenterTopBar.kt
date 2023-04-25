package hu.tb.tasky.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import hu.tb.tasky.R
import androidx.compose.ui.text.style.TextOverflow
import hu.tb.tasky.model.SortTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    dropDownMenuList: List<SortTask>
) {
    var isMenuVisible by rememberSaveable { mutableStateOf(false) }

    return CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = R.string.your_tasks),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = {
                isMenuVisible = true
            }) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Sort menu"
                )
            }
            DropdownMenu(
                expanded = isMenuVisible,
                onDismissRequest = { isMenuVisible = false }
            ) {
                dropDownMenuList.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            it.onClick()
                            isMenuVisible = false
                        }
                    )
                }
            }
        }
    )
}
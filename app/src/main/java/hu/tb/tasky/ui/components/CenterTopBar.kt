package hu.tb.tasky.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import hu.tb.tasky.R
import hu.tb.tasky.domain.util.Sort
import hu.tb.tasky.domain.util.SortType
import hu.tb.tasky.model.SortTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    dropDownMenuList: List<SortTask>,
) {
    var isMenuVisible by rememberSaveable { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(1) }
    var lastSelectedItemIndex by rememberSaveable { mutableStateOf(-1) }
    var isAscendingOrder by rememberSaveable { mutableStateOf(true) }

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
                dropDownMenuList.forEachIndexed { index, item ->
                    val isSelected = index == selectedItemIndex
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            isAscendingOrder = if (lastSelectedItemIndex == index) {
                                !isAscendingOrder
                            } else {
                                true
                            }
                            lastSelectedItemIndex = index
                            when (index) {
                                0 -> {
                                    if (isAscendingOrder) item.onClick(Sort.Title(SortType.Ascending)) else item.onClick(
                                        Sort.Title(SortType.Descending)
                                    )
                                }
                                1 -> {
                                    if (isAscendingOrder) item.onClick(Sort.Date(SortType.Ascending)) else item.onClick(
                                        Sort.Date(SortType.Descending)
                                    )
                                }
                            }
                            selectedItemIndex = index
                            isMenuVisible = false
                        },
                        trailingIcon = {
                            if (isSelected) {
                                Icon(
                                    Icons.Outlined.Check,
                                    contentDescription = "Selected"
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}
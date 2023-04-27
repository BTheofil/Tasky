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
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.domain.util.*
import hu.tb.tasky.model.SortTask
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    dropDownMenuList: List<SortTask>,
    dataStoreProto: DataStoreProtoRepository,
) {
    val appSettings = dataStoreProto.appSettings.collectAsState(
        initial = AppSettings()
    ).value
    val scope = rememberCoroutineScope()
    var isMenuVisible by rememberSaveable { mutableStateOf(false) }
    var selectedItemIndex: Int? by rememberSaveable { mutableStateOf(null) }
    var lastSelectedItemIndex: Int? by rememberSaveable { mutableStateOf(null) }
    var isAscendingOrder: Boolean? by rememberSaveable { mutableStateOf(null) }

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
                appSettings.sortBy.ordinal.apply {
                    selectedItemIndex = this
                    lastSelectedItemIndex = this
                }
                isAscendingOrder = appSettings.sortTYPE.value
                dropDownMenuList.forEachIndexed { index, item ->
                    val isSelected = index == selectedItemIndex
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            isAscendingOrder = if (lastSelectedItemIndex == index) {
                                !isAscendingOrder!!
                            } else {
                                true
                            }
                            lastSelectedItemIndex = index
                            when (index) {
                                0 -> {
                                    if (isAscendingOrder!!) {
                                        item.onClick(Order.TITLE, OrderType.ASCENDING)
                                        scope.launch {
                                            dataStoreProto.saveSort(Order.TITLE, OrderType.ASCENDING)
                                        }
                                    } else {
                                        item.onClick(Order.TITLE, OrderType.DESCENDING)
                                        scope.launch {
                                            dataStoreProto.saveSort(Order.TITLE, OrderType.DESCENDING)
                                        }
                                    }
                                }
                                1 -> {
                                    if (isAscendingOrder!!) {
                                        item.onClick(Order.TIME, OrderType.ASCENDING)
                                        scope.launch {
                                            dataStoreProto.saveSort(Order.TIME, OrderType.ASCENDING)
                                        }
                                    } else {
                                        item.onClick(Order.TIME, OrderType.DESCENDING)
                                        scope.launch {
                                            dataStoreProto.saveSort(Order.TIME, OrderType.DESCENDING)
                                        }
                                    }
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
package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hu.tb.tasky.R
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.ui.components.FloatingActionButtonComponent
import hu.tb.tasky.ui.components.CenterTopBar
import hu.tb.tasky.ui.task_list.component.SortDropdownMenu
import hu.tb.tasky.ui.task_list.component.SurelyDeleteDialog
import hu.tb.tasky.ui.task_list.component.TaskListContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    state: TaskListState,
    navController: NavHostController,
    onEvent: (TaskListEvent) -> Unit,
    protoData: DataStoreProtoRepository,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        state.listEntityWithTaskAllList.size
    }

    var isCreateDialogShow by remember { mutableStateOf(false) }
    var isSortDropdownMenuVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (state.listEntityWithTaskAllList.isNotEmpty()) {
            onEvent(
                TaskListEvent.ChangeActiveList(
                    state.listEntityWithTaskAllList[pagerState.currentPage].list
                )
            )
        }
    }

    LaunchedEffect(key1 = state.createNewListDialogHasError){
        if (state.createNewListDialogHasError == CreateNewListDialogState.OK) {
            isCreateDialogShow = false
        }
    }

    SortDropdownMenu(
        isVisible = isSortDropdownMenuVisible,
        onDismissRequest = { isSortDropdownMenuVisible = false },
        onSortClick = { order, type ->
            isSortDropdownMenuVisible = false
            onEvent(TaskListEvent.OnSortButtonClick(order, type))
        },
        dataStoreProto = protoData,
    )

    if (isCreateDialogShow) {
        SurelyDeleteDialog(
            onDismissRequest = {
                isCreateDialogShow = false
                onEvent(TaskListEvent.ClearDialogState)
            },
            onPositiveBtnClick = { onEvent(TaskListEvent.SaveList) },
            onNegativeBtnClick = {
                isCreateDialogShow = false
                onEvent(TaskListEvent.ClearDialogState)
            },
            valueText = state.newListName,
            onValueChange = { onEvent(TaskListEvent.OnCreateNewListTextChange(it)) },
            isDialogError = state.createNewListDialogHasError,
        )
    }

    Scaffold(
        topBar = {
            CenterTopBar()
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {
                        if (state.activeListEntity.listId != 1) {
                            onEvent(TaskListEvent.OnListDelete(state.activeListEntity))
                        }
                    }) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Delete button")
                    }
                    IconButton(onClick = {
                        isSortDropdownMenuVisible = !isSortDropdownMenuVisible
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_sort_24),
                            contentDescription = "Sort tasks icon"
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButtonComponent(
                        listId = state.activeListEntity.listId,
                        navController = navController
                    )
                },
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding()),
        ) {
            ScrollableTabRow(
                modifier = Modifier
                    .fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp,
                divider = {
                    // need to empty
                }
            ) {
                state.listEntityWithTaskAllList.forEachIndexed { index, listWithTask ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = listWithTask.list.name) }
                    )
                }
                Tab(selected = false,
                    onClick = { isCreateDialogShow = true }
                ) {
                    Row {
                        Icon(Icons.Outlined.Create, contentDescription = "Create new list")
                        Text(text = stringResource(R.string.create_new_list))
                    }
                }
            }
            Divider(modifier = Modifier.fillMaxWidth())
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { index ->
                TaskListContent(
                    listId = if (state.listEntityWithTaskAllList.isEmpty()) 0 else state.listEntityWithTaskAllList[index].list.listId,
                    items = if (state.listEntityWithTaskAllList.isEmpty()) emptyList() else state.listEntityWithTaskAllList[index].listOfTask,
                    navController = navController,
                    onEvent = onEvent,
                )
            }
        }
    }
}

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hu.tb.tasky.R
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.model.SortTask
import hu.tb.tasky.ui.components.FloatingActionButtonComponent
import hu.tb.tasky.ui.components.TopBar
import hu.tb.tasky.ui.task_list.component.SurelyDeleteDialog
import hu.tb.tasky.ui.task_list.component.TaskListContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    taskListState: TaskListState,
    navController: NavHostController,
    onEvent: (TaskListEvent) -> Unit,
    protoData: DataStoreProtoRepository,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        taskListState.listEntityWithTaskAllList.size
    }

    var isCreateDialogShow by remember { mutableStateOf(false) }

    val dropDownMenuList = listOf(
        SortTask(stringResource(id = R.string.sort_name)) { order, orderType ->
            onEvent(TaskListEvent.OnSortButtonClick(order, orderType))
        },
        SortTask(stringResource(id = R.string.sort_time)) { order, orderType ->
            onEvent(
                TaskListEvent.OnSortButtonClick(order, orderType)
            )
        },
    )

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (taskListState.listEntityWithTaskAllList.isNotEmpty()) {
            onEvent(
                TaskListEvent.ChangeActiveList(
                    taskListState.listEntityWithTaskAllList[pagerState.currentPage].list
                )
            )
        }
    }

    if (isCreateDialogShow) {
        SurelyDeleteDialog(
            onDismissRequest = { isCreateDialogShow = false },
            onPositiveBtnClick = {
                onEvent(TaskListEvent.SaveList)
                if (!taskListState.createNewListDialogHasError) {
                    isCreateDialogShow = false
                }
            },
            onNegativeBtnClick = {
                isCreateDialogShow = false
                onEvent(TaskListEvent.ClearDialogState)
            },
            valueText = taskListState.newListName,
            onValueChange = { onEvent(TaskListEvent.OnCreateNewListTextChange(it)) },
            isDialogError = taskListState.createNewListDialogHasError,
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                dropDownMenuList = dropDownMenuList,
                protoData,
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {
                        if (taskListState.activeListEntity.listId != 1) {
                            onEvent(TaskListEvent.OnListDelete(taskListState.activeListEntity))
                        }
                    }) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Delete button")
                    }
                },
                floatingActionButton = {
                    FloatingActionButtonComponent(
                        listId = taskListState.activeListEntity.listId,
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
                divider = {}
            ) {
                taskListState.listEntityWithTaskAllList.forEachIndexed { index, listWithTask ->
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
                    listId = if (taskListState.listEntityWithTaskAllList.isEmpty()) 0 else taskListState.listEntityWithTaskAllList[index].list.listId,
                    items = if (taskListState.listEntityWithTaskAllList.isEmpty()) emptyList() else taskListState.listEntityWithTaskAllList[index].listOfTask,
                    navController = navController,
                    onEvent = onEvent,
                )
            }
        }
    }
}

package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import hu.tb.tasky.R
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.model.SortTask
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.ui.components.FloatingActionButtonComponent
import hu.tb.tasky.ui.components.TopBar
import hu.tb.tasky.ui.route.RouteNames
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
    val pagerState = rememberPagerState(initialPage = 0)

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

    LaunchedEffect(key1 = pagerState.currentPage){
        if (taskListState.listEntityWithTaskAllList.isNotEmpty()){
            onEvent(TaskListEvent.ChangeActiveList(
                taskListState.listEntityWithTaskAllList[pagerState.currentPage].list
            ))
        }
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
                              if(taskListState.activeListEntity.listId != 1){
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
            if (taskListState.listEntityWithTaskAllList.size < 4) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage
                ) {
                    taskListState.listEntityWithTaskAllList.forEachIndexed { index, listWithTask ->
                        Tab(selected = pagerState.currentPage == index,
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
                        Icon(Icons.Outlined.List, contentDescription = "asd") //TODO better icon
                    }
                }
            } else {
                ScrollableTabRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 0.dp
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
                    Tab(
                        selected = false,
                        onClick = { isCreateDialogShow = true }
                    ) {
                        Icon(Icons.Outlined.List, contentDescription = "create new list")
                    }
                }
            }
            HorizontalPager(
                pageCount = taskListState.listEntityWithTaskAllList.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { index ->
                TaskListContent(
                    listId = taskListState.listEntityWithTaskAllList[index].list.listId,
                    items = taskListState.listEntityWithTaskAllList[index].listOfTask,
                    navController = navController,
                    onEvent = onEvent,
                )
            }
        }
        if (isCreateDialogShow) {
            Dialog(onDismissRequest = { isCreateDialogShow = false }) {
                Column {
                    TextField(
                        value = taskListState.newListName,
                        onValueChange = { onEvent(TaskListEvent.OnCreateNewListTextChange(it)) })
                    Button(onClick = {
                        onEvent(TaskListEvent.OnAddListClick)
                        isCreateDialogShow = false
                    }) {
                        Text(text = "Create")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskListContent(
    listId: Int,
    items: List<TaskEntity>,
    navController: NavController,
    onEvent: (TaskListEvent) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        items(
            items = items,
            key = { task -> task.taskId!! }
        ) { task ->
            TaskItemContainer(
                modifier = Modifier
                    .animateItemPlacement()
                    .clickable {
                        navController.navigate(RouteNames.ADD_EDIT_SCREEN + "/listId=$listId?editedTask=${task.taskId}")
                    }
                    .height(IntrinsicSize.Max),
                taskItem = task,
                isDone = task.isTaskDone,
                onDoneClick = { onEvent(TaskListEvent.OnDoneClick(task, it)) }
            )
        }
    }
}

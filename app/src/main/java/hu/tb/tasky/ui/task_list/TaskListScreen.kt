package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    val tabTitleNames = mutableListOf(R.string.done, R.string.ongoing)

    val dropDownMenuList = listOf(
        SortTask(stringResource(id = R.string.sort_name)) { order, orderType ->
            onEvent(TaskListEvent.OnSortButtonClick(order, orderType))
        },
        SortTask(stringResource(id = R.string.sort_time)) { order, orderType ->
            onEvent(
                TaskListEvent.OnSortButtonClick(
                    order,
                    orderType
                )
            )
        },
    )

    Scaffold(
        topBar = {
            TopBar(
                dropDownMenuList = dropDownMenuList,
                protoData,
            )
        },
        floatingActionButton = { FloatingActionButtonComponent(navController = navController) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding()),
        ) {
            if (taskListState.listNamesList.size < 4) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage
                ) {
                    taskListState.listNamesList.forEachIndexed { index, listEntity ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = listEntity.name,
                                )
                            }
                        )
                    }
                    Tab(
                        selected = true,
                        onClick = { onEvent(TaskListEvent.OnAddListClick) }
                    ) {
                        Icon(Icons.Outlined.List, contentDescription = "asd")
                    }
                }
            } else {
                ScrollableTabRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 0.dp
                ) {
                    taskListState.listNamesList.forEachIndexed { index, listEntity ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = listEntity.name,
                                )
                            }
                        )
                    }
                    Tab(
                        selected = true,
                        onClick = { onEvent(TaskListEvent.OnAddListClick) }
                    ) {
                        Icon(Icons.Outlined.List, contentDescription = "asd")
                    }
                }
            }
            HorizontalPager(
                pageCount = taskListState.listNamesList.size,
                state = pagerState
            ) { index ->
                taskListState.listWithTask.forEach {
                    if (index == it.list.listId) {
                        TaskListContent(
                            items = it.listOfTask,
                            navController = navController,
                            onEvent = onEvent,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListContent(
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
                        navController.navigate(RouteNames.ADD_EDIT_SCREEN + "?editedTask=${task.taskId}")
                    }
                    .height(IntrinsicSize.Max),
                taskItem = task,
                isDone = task.isTaskDone,
                onDoneClick = { onEvent(TaskListEvent.OnDoneClick(task, it)) }
            )
        }
    }
}

object TabTitles {
    //const val DONE_TASKS = 0
    const val ONGOING_TASKS = 1
}

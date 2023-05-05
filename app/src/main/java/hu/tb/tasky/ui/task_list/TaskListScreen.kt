package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import hu.tb.tasky.ui.task_list.TabTitles.ONGOING_TASKS
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    taskListState: TaskListState,
    navController: NavHostController,
    onEvent: (TaskListEvent) -> Unit,
    protodata: DataStoreProtoRepository,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = ONGOING_TASKS)
    val tabTitleNames = listOf(R.string.done, R.string.ongoing)

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
                protodata,
            )
        },
        floatingActionButton = { FloatingActionButtonComponent(navController = navController) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding()),
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabTitleNames.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(id = title),
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                pageCount = tabTitleNames.size,
                state = pagerState
            ) { index ->
                taskListState.taskMapList.forEach {
                    if (index == it.key) {
                        TaskListContent(
                            items = it.value,
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
            key = { task -> task.id!! }
        ) { task ->
            TaskItemContainer(
                modifier = Modifier
                    .animateItemPlacement()
                    .clickable {
                        navController.navigate(RouteNames.ADD_EDIT_SCREEN + "?editedTask=${task.id}")
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

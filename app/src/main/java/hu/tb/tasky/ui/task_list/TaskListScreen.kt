package hu.tb.tasky.ui.task_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.tb.tasky.R
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.ui.components.FloatingActionButtonComponent
import hu.tb.tasky.ui.components.TopBar
import hu.tb.tasky.ui.route.RouteNames
import hu.tb.tasky.ui.task_list.TabTitles.ONGOING_TASKS
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    taskListViewModel: TaskListViewModel = hiltViewModel(),
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val ongoingTaskList: List<TaskEntity> by taskListViewModel.ongoingTaskList.collectAsState()
    val doneTaskList: List<TaskEntity> by taskListViewModel.doneTaskList.collectAsState()
    val pagerState = rememberPagerState(initialPage = ONGOING_TASKS)
    val tabTitleNames = listOf(R.string.done, R.string.ongoing)

    Scaffold(
        topBar = { TopBar() },
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
            TabContent(
                tabTitleNames.size,
                ongoingTaskList,
                doneTaskList,
                taskListViewModel,
                navController,
                pagerState,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(
    tabCount: Int,
    ongoingTaskList: List<TaskEntity>,
    doneTaskList: List<TaskEntity>,
    taskListViewModel: TaskListViewModel,
    navController: NavController,
    state: PagerState,
) {
    HorizontalPager(
        pageCount = tabCount,
        state = state
    ) { index ->
        when (index) {
            0 -> {
                TaskListContent(
                    items = doneTaskList,
                    navController = navController,
                    taskListViewModel = taskListViewModel,
                )
            }
            1 -> {
                TaskListContent(
                    items = ongoingTaskList,
                    navController = navController,
                    taskListViewModel = taskListViewModel,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListContent(
    items: List<TaskEntity>,
    navController: NavController,
    taskListViewModel: TaskListViewModel
){
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
                    },
                taskItem = task,
                isDone = task.isTaskDone,
                onDoneClick = {
                    taskListViewModel.onEvent(
                        TaskListEvent.OnDoneClick(
                            task,
                            it
                        )
                    )
                }
            )
        }
    }
}

object TabTitles {
    //const val DONE_TASKS = 0
    const val ONGOING_TASKS = 1
}

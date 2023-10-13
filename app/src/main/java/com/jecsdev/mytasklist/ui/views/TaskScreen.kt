package com.jecsdev.mytasklist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jecsdev.mytasklist.R
import com.jecsdev.mytasklist.ui.event.TaskEvent
import com.jecsdev.mytasklist.ui.navigation.Screen
import com.jecsdev.mytasklist.ui.viewModels.TasksViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isLoadingState by remember { mutableStateOf(state.isTaskVisible) }
    val alpha by animateFloatAsState(targetValue = if (isLoadingState) 0f else 10f, animationSpec = tween(2000),
        label = ""
    )

    LaunchedEffect(key1 = true){
        delay(1000)
        isLoadingState = false
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditTaskScreen.route)
            }, backgroundColor = MaterialTheme.colors.primary) {
            }
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = context.getString(R.string.your_task_list_description),
                modifier = Modifier.offset(x = 16.dp, y = 16.dp),
                tint = Color.White
            )
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        paddingValues.calculateBottomPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.my_tasks),
                    style = MaterialTheme.typography.h4
                )
                IconButton(onClick = {
                    viewModel.onEvent(TaskEvent.ToggleOrderSection)
                }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = context.getString(R.string.sort)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSelectionVisible
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    taskOrder = state.taskOrder,
                    onOrderChange = { taskOrder ->
                        viewModel.onEvent(TaskEvent.Order(taskOrder))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoadingState) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(state.tasks) { task ->
                            this@Column.AnimatedVisibility(
                                visible = !isLoadingState,
                                enter = slideInHorizontally(tween(300)),
                                exit = slideOutVertically(tween(300))
                            ) {
                                TaskItem(task = task,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateEnterExit()
                                        .alpha(alpha)
                                        .clickable {
                                            navController.navigate(
                                                Screen.AddEditTaskScreen.route + "?taskId=${task.id}&?taskColor=${task.color}"
                                            )
                                        },
                                    onDeleteClick = {
                                        viewModel.onEvent(TaskEvent.DeleteTask(task))
                                        scope.launch {
                                            val result =
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = context.getString(R.string.task_deleted),
                                                    actionLabel = context.getString(R.string.undo_uppercase)
                                                )
                                            if (result == SnackbarResult.ActionPerformed) {
                                                viewModel.onEvent(TaskEvent.RestoreTask)
                                            }
                                        }
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

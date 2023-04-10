package com.jecs.mytasklist.ui.components
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jecs.mytasklist.ui.event.TaskEvent
import com.jecs.mytasklist.ui.navigation.Screen
import com.jecs.mytasklist.ui.viewModels.TasksViewModel
import kotlinx.coroutines.launch
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
) {
        val state = viewModel.state.value
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditTaskScreen.route)
            }, backgroundColor = MaterialTheme.colors.primary) {
            }
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add task",
                modifier = Modifier.offset(x = 16.dp, y = 16.dp),
                tint = Color.White
            )
        },
        scaffoldState = scaffoldState
        ) {it.calculateBottomPadding()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Your task",
                        style = MaterialTheme.typography.h4
                    )
                    IconButton(onClick = {
                        viewModel.onEvent(TaskEvent.ToggleOrderSection)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSelectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically(),
                ){
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        taskOrder = state.taskOrder,
                        onOrderChange = {
                            viewModel.onEvent(TaskEvent.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(state.tasks){task ->
                        TaskItem(task = task,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                       navController.navigate(
                                           Screen.AddEditTaskScreen.route + "?taskId=${task.id}&?taskColor=${task.color}"
                                       )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(TaskEvent.DeleteTask(task))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Task deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(TaskEvent.RestoreTask)
                                }
                            }
                        }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
}
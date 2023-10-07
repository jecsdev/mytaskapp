package com.jecsdev.mytasklist.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.mytasklist.feature_task.domain.model.Task

import com.jecsdev.mytasklist.feature_task.domain.use_case.TaskUseCases
import com.jecsdev.mytasklist.feature_task.domain.util.OrderType
import com.jecsdev.mytasklist.feature_task.domain.util.TaskOrder
import com.jecsdev.mytasklist.ui.event.TaskEvent
import com.jecsdev.mytasklist.ui.state.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state

    private var recentlyDeletedTask: Task? = null
    private var getTasksJob: Job? = null
    init{
        getTask(TaskOrder.Date(OrderType.Descending))
    }
    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.Order -> {
                if (state.value.taskOrder::class == event.taskOrder::class
                    && state.value.taskOrder.orderType == event.taskOrder.orderType) {
                    return
                }
                getTask(event.taskOrder)
            }
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeletedTask = event.task
                }
            }

            is TaskEvent.RestoreTask -> {
                viewModelScope.launch {
                    taskUseCases.addTask(recentlyDeletedTask ?: return@launch)
                    recentlyDeletedTask = null
                }
            }

            is TaskEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSelectionVisible = !state.value.isOrderSelectionVisible
                )
            }
        }
    }

    private fun getTask(taskOrder: TaskOrder) {
        getTasksJob?.cancel()
        getTasksJob = taskUseCases.getTasks(taskOrder)
            .onEach {task->
                _state.value = state.value.copy(tasks = task,
                taskOrder = taskOrder)
            }
            .launchIn(viewModelScope)
    }
}
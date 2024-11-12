package com.jecsdev.mytasklist.ui.state

import com.jecsdev.mytasklist.feature_task.data.data_source.model.Task
import com.jecsdev.mytasklist.feature_task.domain.util.OrderType
import com.jecsdev.mytasklist.feature_task.domain.util.TaskOrder

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: TaskOrder = TaskOrder.Date(OrderType.Descending),
    val isTaskVisible: Boolean = true,
    val isOrderSelectionVisible: Boolean = false
)
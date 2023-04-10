package com.jecs.mytasklist.ui.state

import com.jecs.mytasklist.feature_task.domain.model.Task
import com.jecs.mytasklist.feature_task.domain.util.OrderType
import com.jecs.mytasklist.feature_task.domain.util.TaskOrder

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: TaskOrder = TaskOrder.Date(OrderType.Descending),
    val isOrderSelectionVisible: Boolean = false
)
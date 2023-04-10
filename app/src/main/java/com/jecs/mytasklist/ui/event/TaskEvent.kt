package com.jecs.mytasklist.ui.event

import com.jecs.mytasklist.feature_task.domain.model.Task
import com.jecs.mytasklist.feature_task.domain.util.TaskOrder

sealed class TaskEvent{
    data class Order(val taskOrder: TaskOrder): TaskEvent()
    data class DeleteTask(val task: Task): TaskEvent()
    object RestoreTask: TaskEvent()
    object ToggleOrderSection: TaskEvent()
}

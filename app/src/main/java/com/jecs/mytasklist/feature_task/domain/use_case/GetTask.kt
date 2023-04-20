package com.jecs.mytasklist.feature_task.domain.use_case

import com.jecs.mytasklist.feature_task.domain.model.EmptyTask
import com.jecs.mytasklist.feature_task.domain.model.Task
import com.jecs.mytasklist.feature_task.domain.model.toTask
import com.jecs.mytasklist.feature_task.domain.repository.TaskRepository

class GetTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task {
        return repository.getTaskById(id) ?: EmptyTask().toTask()
    }
}
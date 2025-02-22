package com.jecsdev.mytasklist.feature_task.domain.repository

import com.jecsdev.mytasklist.feature_task.data.data_source.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasks(): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)
}
package com.jecsdev.mytasklist.feature_task.data.repository

import com.jecsdev.mytasklist.feature_task.data.data_source.dao.TaskDao
import com.jecsdev.mytasklist.feature_task.data.data_source.model.Task
import com.jecsdev.mytasklist.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImplementation(private val dao: TaskDao): TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}
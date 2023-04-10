package com.jecs.mytasklist.feature_task.domain.repository

import com.jecs.mytasklist.feature_task.domain.model.InvalidTaskException
import com.jecs.mytasklist.feature_task.domain.model.Task

class AddTask(
    private val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task){
        if(task.title.isBlank()){
            throw InvalidTaskException("The title cannot be empty.")
        }

        if(task.content.isBlank()){
            throw InvalidTaskException("The content cannot be empty.")
        }

        repository.insertTask(task)
    }
}
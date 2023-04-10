package com.jecs.mytasklist.feature_task.domain.use_case

import com.jecs.mytasklist.feature_task.domain.repository.AddTask

data class TaskUseCases(val getTasks: GetTasks,
                        val deleteTask: DeleteTask,
                        val addTask: AddTask,
                        val getTask: GetTask
                        )

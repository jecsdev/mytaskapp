package com.jecsdev.mytasklist.feature_task.domain.use_case

import com.jecsdev.mytasklist.feature_task.data.data_source.model.Task
import com.jecsdev.mytasklist.feature_task.domain.repository.TaskRepository
import com.jecsdev.mytasklist.feature_task.domain.util.OrderType
import com.jecsdev.mytasklist.feature_task.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(private val repository: TaskRepository) {

    operator fun invoke(taskOrder: TaskOrder = TaskOrder.Date(OrderType.Descending)): Flow<List<Task>>{
            return repository.getTasks().map { task ->
                when(taskOrder.orderType){
                    is OrderType.Ascending -> {
                        when(taskOrder){
                            is TaskOrder.Title -> task.sortedBy { it.title.lowercase() }
                            is TaskOrder.Date -> task.sortedBy { it.date }
                            is TaskOrder.Color ->task.sortedBy { it.color }
                        }
                    }
                    is OrderType.Descending -> {
                        when(taskOrder){
                            is TaskOrder.Title -> task.sortedByDescending { it.title.lowercase() }
                            is TaskOrder.Date -> task.sortedByDescending { it.date }
                            is TaskOrder.Color -> task.sortedByDescending { it.color }
                        }
                    }
                }
            }
    }
}
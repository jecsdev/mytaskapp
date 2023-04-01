package com.jecs.mytasklist.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jecs.mytasklist.feature_task.domain.use_case.TaskUseCases
import com.jecs.mytasklist.feature_task.presentation.TaskEvent
import com.jecs.mytasklist.feature_task.presentation.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
): ViewModel(){

    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state

    fun onEvent(event: TaskEvent){
        when(event){
            is TaskEvent.Order ->{

            }
            is TaskEvent.DeleteTask ->{

            }
            is TaskEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSelectionVisible = !state.value.isOrderSelectionVisible
                )
            }
            else -> {}
        }
    }
}
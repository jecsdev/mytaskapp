package com.jecs.mytasklist.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecs.mytasklist.feature_task.domain.model.InvalidTaskException
import com.jecs.mytasklist.feature_task.domain.model.Task
import com.jecs.mytasklist.feature_task.domain.use_case.TaskUseCases
import com.jecs.mytasklist.ui.event.AddEditTaskEvent
import com.jecs.mytasklist.ui.event.UiEvent
import com.jecs.mytasklist.ui.state.TaskTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {
    private val _taskTitle = mutableStateOf(TaskTextFieldState(
        hint = "Enter title..."
    ))
    val taskTitle: State<TaskTextFieldState> = _taskTitle

    private val _taskContent = mutableStateOf(TaskTextFieldState(
        hint = "Type some content..."
    ))
    val taskContent: State<TaskTextFieldState> = _taskContent

    private val _taskColor = mutableStateOf<Int>(Task.taskColor.random().toArgb())
    val taskColor: State<Int> = _taskColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = 0
    init {
        savedStateHandle.get<Int>("taskId")?.let{taskId ->
            if(taskId != -1){
                viewModelScope.launch{
                    taskUseCases.getTask(taskId).also {task ->
                        currentTaskId = task.id
                        _taskTitle.value = taskTitle.value.copy(
                            text = task.title,
                            isHintVisible = false
                        )
                        _taskColor.value = task.color
                        _taskContent.value = taskContent.value.copy(
                            text = task.content,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }
    fun onEvent(event: AddEditTaskEvent){
        when(event){
            is AddEditTaskEvent.EnteredTitle -> {
                _taskTitle.value = taskTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeTitleFocus ->{
                _taskTitle.value = taskTitle.value.copy(
                    isHintVisible = !event.value.isFocused && taskTitle.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.EnteredContent -> {
                _taskContent.value = taskContent.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeContentFocus ->{
                _taskContent.value = taskContent.value.copy(
                    isHintVisible = !event.value.isFocused && taskContent.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.ChangeColor -> {
                _taskColor.value = event.color
            }
            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.addTask(
                            Task(
                                title = taskTitle.value.text,
                                content = taskContent.value.text,
                                date = System.currentTimeMillis(),
                                color = taskColor.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTask)
                    }catch (e: InvalidTaskException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBarr(
                                message = e.message ?: "Unfortunately, task is not saved."
                            )
                        )
                    }
                }
            }
        }
    }
}
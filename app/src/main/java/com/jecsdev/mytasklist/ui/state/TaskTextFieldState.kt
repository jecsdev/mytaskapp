package com.jecsdev.mytasklist.ui.state

data class TaskTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)

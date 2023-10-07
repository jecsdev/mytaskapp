package com.jecsdev.mytasklist.ui.event

sealed class UiEvent{
    data class ShowSnackBarr(val message: String): UiEvent()
    object SaveTask: UiEvent()
}

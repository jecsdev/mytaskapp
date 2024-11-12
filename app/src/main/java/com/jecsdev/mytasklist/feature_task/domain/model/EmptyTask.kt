package com.jecsdev.mytasklist.feature_task.domain.model

import android.graphics.Color
import com.jecsdev.mytasklist.feature_task.data.data_source.model.Task

data class EmptyTask(
    val id: Int = -1, val color: Int = Color.BLACK, val title: String = "",
    val content: String = "", val date: Long = 0)

fun EmptyTask.toTask() = Task(id = id, color = color, title = title, content = content, date = date)
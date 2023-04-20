package com.jecs.mytasklist.feature_task.domain.model

import android.graphics.Color

data class EmptyTask(
    val id: Int = -1, val color: Int = Color.BLACK, val title: String = "",
    val content: String = "", val date: Long = 0)

fun EmptyTask.toTask() = Task(id = id, color = color, title = title, content = content, date = date)
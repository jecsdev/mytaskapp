package com.jecs.mytasklist.feature_task.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jecs.mytasklist.ui.theme.*

@Entity
data class Task(
    var title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object {
        val taskColor = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

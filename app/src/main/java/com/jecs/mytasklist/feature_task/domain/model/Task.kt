package com.jecs.mytasklist.feature_task.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jecs.mytasklist.ui.theme.*

@Entity
data class Task(
    @ColumnInfo("title") val title: String,
    @ColumnInfo("content")val content: String,
    @ColumnInfo("date")val date: Long,
    @ColumnInfo("color") val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
){
    companion object {
        val taskColor = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidTaskException(message:String): Exception(message)
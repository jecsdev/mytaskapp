package com.jecsdev.mytasklist.feature_task.data.data_source.dbcontext

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jecsdev.mytasklist.feature_task.data.data_source.DAO.TaskDao
import com.jecsdev.mytasklist.feature_task.domain.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase(){
    abstract val taskDao: TaskDao
    companion object {
        const val DATABASE_NAME = "Tasks"

    }
}
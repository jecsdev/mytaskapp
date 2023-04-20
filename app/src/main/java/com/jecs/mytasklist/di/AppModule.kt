package com.jecs.mytasklist.di

import android.app.Application
import androidx.room.Room
import com.jecs.mytasklist.feature_task.data.data_source.dbcontext.TaskDatabase
import com.jecs.mytasklist.feature_task.data.repository.TaskRepositoryImplementation
import com.jecs.mytasklist.feature_task.domain.use_case.AddTask
import com.jecs.mytasklist.feature_task.domain.repository.TaskRepository
import com.jecs.mytasklist.feature_task.domain.use_case.DeleteTask
import com.jecs.mytasklist.feature_task.domain.use_case.GetTask
import com.jecs.mytasklist.feature_task.domain.use_case.GetTasks
import com.jecs.mytasklist.feature_task.domain.use_case.TaskUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImplementation(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getTasks = GetTasks(repository),
            deleteTask = DeleteTask(repository),
            addTask = AddTask(repository),
            getTask = GetTask(repository)
        )
    }
}
package com.jecsdev.mytasklist.feature_task.domain.use_case

import android.graphics.Color
import com.jecsdev.mytasklist.feature_task.data.data_source.model.Task
import com.jecsdev.mytasklist.feature_task.domain.model.EmptyTask
import com.jecsdev.mytasklist.feature_task.domain.model.toTask
import com.jecsdev.mytasklist.feature_task.domain.repository.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId


class GetTaskTest{
     @RelaxedMockK
     private lateinit var taskRepository: TaskRepository

     lateinit var getTask: GetTask

     @Before
     fun onBefore(){
         MockKAnnotations.init(this)
         getTask = GetTask(taskRepository)
     }

    @Test
    fun `when task id does not exist return avoid null`() = runBlocking{
        //Given
        val emptyTask = EmptyTask().toTask()
        coEvery { taskRepository.getTaskById(-1) } returns emptyTask

        //When
        val response = getTask(-1)
        //Then
        assert(response == emptyTask)
    }

    @Test
    fun `when task id exists return task object`() = runBlocking {
        //Given
        val time = LocalDateTime.now()
        val zoneId = ZoneId.systemDefault()
        val epoch = time.atZone(zoneId).toEpochSecond()
        val task = Task(color = Color.CYAN, title = "Example title", content = "Example Content",
            date = epoch, id = 1
            )
        coEvery { taskRepository.getTaskById(task.id) } returns task
        //When
        val response = getTask(task.id)

        //Then
        assert(response.id == task.id)

    }

    @After
    fun tearDown(){
        unmockkAll()
    }

 }
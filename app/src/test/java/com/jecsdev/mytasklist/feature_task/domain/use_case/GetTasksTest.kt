package com.jecsdev.mytasklist.feature_task.domain.use_case

import android.graphics.Color
import com.jecsdev.mytasklist.feature_task.data.data_source.model.Task
import com.jecsdev.mytasklist.feature_task.domain.repository.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId


class GetTasksTest{
    @RelaxedMockK
    private lateinit var taskRepository: TaskRepository

    lateinit var getTasks: GetTasks

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getTasks = GetTasks(taskRepository)
    }

    @Test
    fun `when the database is empty return an empty list`() = runBlocking{
        //Given
        coEvery { taskRepository.getTasks() } returns emptyFlow()
        //When
        getTasks()
        //Then
        coVerify { taskRepository.getTasks() }

    }

    @Test
    fun `when the database is not empty then return a task`()  = runBlocking {
        //Given
        val time =LocalDateTime.now()
        val zoneId = ZoneId.systemDefault()
        val epoch = time.atZone(zoneId).toEpochSecond()
        val taskList = flow {
            emit(listOf(
                Task(color = Color.BLUE, title = "Task demo", content = "This is the content",
                date = epoch, id = 0)
            ))
        }

        coEvery { taskRepository.getTasks()} returns taskList

        //when
        val response = getTasks()

        //then
        assert(response.first() == taskList.first())
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}
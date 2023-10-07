package com.jecsdev.mytasklist.feature_task.domain.use_case

import android.graphics.Color
import com.jecsdev.mytasklist.feature_task.domain.model.InvalidTaskException
import com.jecsdev.mytasklist.feature_task.domain.model.Task
import com.jecsdev.mytasklist.feature_task.domain.repository.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId


class AddTaskTest {

    @RelaxedMockK
    private lateinit var taskRepository: TaskRepository

    lateinit var addTask: AddTask
    lateinit var getTask: GetTask

    private val time = LocalDateTime.now()
    private val zoneId = ZoneId.systemDefault()
    private val epoch = time.atZone(zoneId).toEpochSecond()
    private val task = Task(color = Color.CYAN, title = "Example title", content = "",
        date = epoch, id = 1
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        addTask = AddTask(taskRepository)
        getTask = GetTask(taskRepository)
    }

    @Test
    fun `when title task field is empty throw error message` () = runBlocking{
        //Given

        val exception = "The title cannot be empty."
        coEvery { taskRepository.insertTask(task) } throws InvalidTaskException(exception)

        //When
        var errorMessage: String? = null
        try {
            addTask(task)
        } catch (e: InvalidTaskException) {
            errorMessage = e.message
        }

        //then

        assert(errorMessage == exception)
    }

    @Test
    fun `when content task field is empty throw error message` () = runBlocking{
        //Given

        val exception = "The content cannot be empty."
        coEvery { taskRepository.insertTask(task) } throws InvalidTaskException(exception)

        //When
        var errorMessage: String? = null
        try {
            addTask(task)
        } catch (e: InvalidTaskException) {
            errorMessage = e.message
        }

        //then

        assert(errorMessage == exception)
    }

    @Test
    fun `task insertion without exceptions`() = runBlocking {
        //Given

        coEvery {  taskRepository.insertTask(task) } returns Unit
        //when
        try {
            taskRepository.insertTask(task)
        } catch (e: Exception) {
            throw InvalidTaskException("Error inserting message: ¨${e.message}")
        }

        //then
        coVerify { taskRepository.insertTask(task) }

    }

    @After
    fun tearDown(){
        unmockkAll()
    }

}
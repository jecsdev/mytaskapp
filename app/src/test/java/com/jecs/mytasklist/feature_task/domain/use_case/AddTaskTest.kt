package com.jecs.mytasklist.feature_task.domain.use_case

import android.graphics.Color
import com.jecs.mytasklist.feature_task.domain.model.InvalidTaskException
import com.jecs.mytasklist.feature_task.domain.model.Task
import com.jecs.mytasklist.feature_task.domain.repository.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId


class AddTaskTest {

    @RelaxedMockK
    private lateinit var taskRepository: TaskRepository

    lateinit var addTask: AddTask
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        addTask = AddTask(taskRepository)
    }

    @Test
    fun `when title task field is empty throw error message` () = runBlocking{
        //Given
        val time = LocalDateTime.now()
        val zoneId = ZoneId.systemDefault()
        val epoch = time.atZone(zoneId).toEpochSecond()
        val task = Task(color = Color.CYAN, title = "", content = "Example Content",
            date = epoch, id = 1
        )
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
        val time = LocalDateTime.now()
        val zoneId = ZoneId.systemDefault()
        val epoch = time.atZone(zoneId).toEpochSecond()
        val task = Task(color = Color.CYAN, title = "Example title", content = "",
            date = epoch, id = 1
        )
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

    }

}
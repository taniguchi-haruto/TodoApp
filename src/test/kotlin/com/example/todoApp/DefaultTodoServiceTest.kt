package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DefaultTodoServiceTest {

    lateinit var mockTodoRepository: TodoRepository
    lateinit var todoService: DefaultTodoService

    @BeforeEach
    fun setUp() {
        mockTodoRepository = mockk()
        todoService = DefaultTodoService(mockTodoRepository)
    }


    @Nested
    inner class Todos {

        @Test
        @DisplayName("todoRepositoryのfindAll()を呼び出す")
        fun `should call todoRepository findAll`() {
            every { mockTodoRepository.findAll() } returns listOf() // return type of findAll() is List<TodoEntity>


            todoService.todos()


            verify { mockTodoRepository.findAll() }
        }

        @Test
        @DisplayName("todoリストを返す")
        fun `should return list of todos`() {
            every { mockTodoRepository.findAll() } returns listOf(TodoEntity(1, "Learn Kotlin"))


            val todos = todoService.todos()


            assertThat(todos.size, equalTo(1))
            assertThat(todos[0].id, equalTo(1))
            assertThat(todos[0].text, equalTo("Learn Kotlin"))
        }
    }

    @Nested
    inner class Create {
        @Test
        @DisplayName("todoRepositoryのsave()を呼び出せる")
        fun `should call todoRepository save`() {
            val todoEntitySave = TodoEntity(0, "Learn Kotlin")
            every { mockTodoRepository.save(todoEntitySave) } returns TodoEntity(1, "Learn Kotlin")


            todoService.create(NewTodoRequest("Learn Kotlin"))


            verify { mockTodoRepository.save(todoEntitySave) }
        }

        @Test
        @DisplayName("新しいIDを返す")
        fun `should return created ID`() {
            every { mockTodoRepository.save(any()) } returns TodoEntity(1, "Learn Kotlin")


            val createdId = todoService.create(NewTodoRequest("Learn Kotlin"))


            assertThat(createdId, equalTo(1))
        }
    }

}
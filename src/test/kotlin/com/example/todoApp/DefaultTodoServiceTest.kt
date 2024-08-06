package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.UpdateTodoRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.*
import org.springframework.data.repository.findByIdOrNull

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

    @Nested
    inner class Update {

        @Test
        @DisplayName("与えられたIDからtodoRepositoryのfindByIdを呼び出す")
        fun `should call todoRepository findById with given id`() {
            every { mockTodoRepository.findByIdOrNull(1) } returns TodoEntity(1, "")
            every { mockTodoRepository.save(any()) } returns TodoEntity(1, "Learn Kotlin")


            todoService.update(1, UpdateTodoRequest("Learn Kotlin"))


            verify { mockTodoRepository.findByIdOrNull(1) }
        }

        @Test
        @DisplayName("更新されたテキストとtodoRepositoryのsaveを呼び出す")
        fun `should call todoRepository save with updated text`() {
            every { mockTodoRepository.findByIdOrNull(any()) } returns TodoEntity(1, "")
            val updatedTodo = TodoEntity(1, "Learn Kotlin")
            every { mockTodoRepository.save(updatedTodo) } returns TodoEntity(1, "Learn Kotlin")


            todoService.update(1, UpdateTodoRequest("Learn Kotlin"))


            verify { mockTodoRepository.save(updatedTodo) }
        }

        @Test
        @DisplayName("更新されたTodoを返す")
        fun `should return updated Todo`() {
            every { mockTodoRepository.findByIdOrNull(any()) } returns TodoEntity(1, "")
            every { mockTodoRepository.save(any()) } returns TodoEntity(1, "Learn Kotlin")


            val actual = todoService.update(1, UpdateTodoRequest("Learn Kotlin"))


            assertThat(actual.id, equalTo(1))
            assertThat(actual.text, equalTo("Learn Kotlin"))
        }

        @Test
        @DisplayName("指定されたIDに該当するTodoが見つからない場合、エラーを返す")
        fun `should throw an error when no Todo is found for the given ID`() {
            every { mockTodoRepository.findByIdOrNull(any()) } returns null


            val error = assertThrows<RuntimeException> {
                todoService.update(1, UpdateTodoRequest(""))
            }


            assertThat(error.message, equalTo("no todo found for id 1"))
        }
    }

    @Nested
    inner class Delete {

        @Test
        @DisplayName("与えられたIDからtodoRepositoryのfindByIdを呼び出す")
        fun `should call todoRepository findById with given id`() {
            every { mockTodoRepository.findByIdOrNull(1) } returns TodoEntity(1, "")
            every { mockTodoRepository.deleteById(any()) } returns Unit


            todoService.delete(1)


            verify { mockTodoRepository.findByIdOrNull(1) }
        }

        @Test
        @DisplayName("与えられたIDからtodoRepositoryのdeleteを呼び出す")
        fun `should call todoRepository delete with given id`() {
            every { mockTodoRepository.findByIdOrNull(1) } returns TodoEntity(1, "Learn Kotlin")
            every { mockTodoRepository.deleteById(1) } returns Unit


            todoService.delete(1)


            verify { mockTodoRepository.deleteById(1) }
        }


        @Test
        @DisplayName("削除されたIdを返す")
        fun `should return deleted Id`() {
            every { mockTodoRepository.findByIdOrNull(any()) } returns TodoEntity(1, "")
            every { mockTodoRepository.deleteById(any()) } returns Unit


            val actual = todoService.delete(1)


            assertThat(actual, equalTo(1))
        }

        @Test
        @DisplayName("指定されたIDに該当するTodoが見つからない場合、エラーを返す")
        fun `should throw an error when no Todo is found for the given ID`() {
            every { mockTodoRepository.findByIdOrNull(any()) } returns null


            val error = assertThrows<RuntimeException> {
                todoService.delete(1)
            }


            assertThat(error.message, equalTo("no todo found for id 1"))
        }
    }
}
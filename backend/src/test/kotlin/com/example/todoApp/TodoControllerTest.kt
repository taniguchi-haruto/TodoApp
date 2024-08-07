package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoControllerTest {

    lateinit var mockTodoService: TodoService
    lateinit var mockMvc: MockMvc
    val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        mockTodoService = mockk()
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(mockTodoService))
            .build()
    }

    @Nested
    inner class GetTodos {
        @Test
        @DisplayName("GETエンドポイントはOKステータスを返す")
        fun `GET endpoint should return status OK`() {
            every { mockTodoService.todos() } returns listOf()


            mockMvc.perform(get("/todos"))
                .andExpect(status().isOk) // check status
        }

        @Test
        @DisplayName("GETリクエストはtodoObjのリストを返す")
        fun `GET endpoint should return list of Todo`() {
            val todoResponse = TodoResponse(1, "Learn Kotlin")
            every { mockTodoService.todos() } returns listOf(todoResponse)
            val expectedJson = """
            [
                {
                    "id": ${todoResponse.id},
                    "text": "${todoResponse.text}"
                }
            ]
        """.trimIndent()


            mockMvc.perform(get("/todos"))
                .andExpect(content().json(expectedJson))
        }

        @Test
        @DisplayName("GETリクエストはtodoServiceからtodos()を返す")
        fun `GET endpoint should call todoService todos()`() {
            every { mockTodoService.todos() } returns listOf() // return type of findAll() is List<TodoEntity>


            mockMvc.perform(get("/todos"))


            verify { mockTodoService.todos() }
        }
    }

    @Nested
    inner class GetTodo {
        @Test
        @DisplayName("GETエンドポイントはOKステータスを返す")
        fun `GET endpoint should return status OK`() {
            every { mockTodoService.todo(any()) } returns TodoResponse(0, "")
            mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk)
        }

        @Test
        @DisplayName("GETリクエストはtodoObjを返す")
        fun `GET endpoint should return Todo`() {
            val todoResponse = TodoResponse(1, "Learn Kotlin")
            every { mockTodoService.todo(any()) } returns todoResponse
            val expectedJson = """
                {
                    "id": ${todoResponse.id},
                    "text": "${todoResponse.text}"
                }
        """.trimIndent()


            mockMvc.perform(get("/todos/1"))
                .andExpect(content().json(expectedJson))
        }

        @Test
        @DisplayName("GETエンドポイントはtodoServiceからtodoを呼び出す")
        fun `GET endpoint should call todoService todo()`() {
            every { mockTodoService.todo(1) } returns TodoResponse(1, "Learn Kotlin")


            mockMvc.perform(get("/todos/1"))


            verify { mockTodoService.todo(1) }
        }
    }

    @Nested
    inner class PostTodo {
        @Test
        @DisplayName("POSTエンドポイントはCREATEDステータスを返す")
        fun `POST endpoint should return status CREATED`() {
            every { mockTodoService.create(any()) } returns 999
            val requestBodyJson = """
            {
                "text": ""
            }
        """.trimIndent()


            mockMvc.perform(
                post("/todos")
                    .content(requestBodyJson)
                    .contentType(APPLICATION_JSON)
            )
                .andExpect(status().isCreated) // check status
        }

        @Test
        @DisplayName("POSTリクエストはtodoServiceにcreate()を返す")
        fun `POST endpoint should call todoService create()`() {
            val todoRequest = NewTodoRequest("Learn Kotlin")
            every { mockTodoService.create(todoRequest) } returns 999

            val requestBodyJson = """
            {
                "text": "${todoRequest.text}"
            }
        """.trimIndent()


            mockMvc.perform(
                post("/todos")
                    .content(requestBodyJson)
                    .contentType(APPLICATION_JSON)
            )


            verify { mockTodoService.create(todoRequest) }
        }

        @Test
        @DisplayName("POSTエンドポイントは新しいIDを返す")
        fun `POST endpoint should return the created ID`() {
            val newId: Long = 9999

            every { mockTodoService.create(any()) } returns newId

            val requestBodyJson = """
            {
                "text": ""
            }
        """.trimIndent()


            val responseBody = mockMvc.perform(
                post("/todos")
                    .content(requestBodyJson)
                    .contentType(APPLICATION_JSON)
            )
                .andReturn()
                .response
                .contentAsString


            assertThat(responseBody, equalTo("$newId"))
        }
    }

    @Nested
    inner class UpdateTodo {
        @Test
        @DisplayName("ステータスOKを返す")
        fun `should return status OK`() {
            every { mockTodoService.update(any(), any()) } returns TodoResponse(999, "Learn Kotlin")
            val requestBodyJson = objectMapper.writeValueAsString(UpdateTodoRequest(""))


            mockMvc.perform(
                put("/todos/999")
                    .content(requestBodyJson)
                    .contentType(APPLICATION_JSON)
            )
                .andExpect(status().isOk)
        }

        @Test
        @DisplayName("todoServiceのupdate()を呼び出す")
        fun `should call todoService update()`() {
            val updateRequest = UpdateTodoRequest("Learn Kotlin")
            every { mockTodoService.update(999, updateRequest) } returns TodoResponse(999, "Learn Kotlin")

            val requestBodyJson = objectMapper.writeValueAsString(updateRequest)


            mockMvc.perform(
                put("/todos/999")
                    .content(requestBodyJson)
                    .contentType(APPLICATION_JSON)
            )


            verify { mockTodoService.update(999, updateRequest) }
        }

        @Test
        @DisplayName("アップデートしたtodoを返す")
        fun `should return updated todo`() {
            val updateRequest = UpdateTodoRequest("Learn Kotlin")
            every {mockTodoService.update(999, updateRequest) } returns TodoResponse(999, "Learn Kotlin")

            val requestBodyJson = objectMapper.writeValueAsString(updateRequest) // UpodateTodoRequest -> json

            val responseJson = mockMvc.perform(
                put("/todos/999")
                    .content(requestBodyJson)
                    .contentType(APPLICATION_JSON)
            ).andReturn()
                .response
                .contentAsString
                .trimIndent()


            // option 1
            // assertThat(responseJson, equalTo("""{"id":999,"text":"Learn Kotlin"}""".trimIndent()))

            // option 2 //
            val todo = objectMapper.readValue<TodoResponse>(responseJson) // json -> TodoResponse


            assertThat(todo.id, equalTo(999))
            assertThat(todo.text, equalTo(updateRequest.text))
        }
    }

    @Nested
    inner class DeleteTodo {
        @Test
        @DisplayName("OKステータスを返す")
        fun `should return status OK`() {
            every { mockTodoService.delete(any()) } returns 999


            mockMvc.perform(delete("/todos/999"))
                .andExpect(status().isOk)
        }

        @Test
        @DisplayName("todoServiceのdelete()を呼び出す")
        fun `should call todoService delete()`() {
            val deletedId: Long = 999
            every { mockTodoService.delete(deletedId) } returns deletedId


            mockMvc.perform(delete("/todos/999"))


            verify { mockTodoService.delete(deletedId) }
        }

        @Test
        @DisplayName("デリートしたIDを返す")
        fun `should return the deleted Id`(){
            val deletedId: Long = 999
            every { mockTodoService.delete(any()) } returns deletedId


            val responseBody = mockMvc.perform(delete("/todos/999"))
                .andReturn()
                .response
                .contentAsString


            assertThat(responseBody, equalTo("$deletedId"))
        }
    }
}
package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoControllerTest {

    lateinit var mockTodoService: TodoService
    lateinit var mockMvc: MockMvc

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
    inner class PostTodo {
        @Test
        @DisplayName("POSTエンドポイントはCREATEDステータスを返す")
        fun `POST endpoint should return status CREATED`() {
            every { mockTodoService.create(any())} returns 999
            val requestBodyJson = """
            {
                "text": ""
            }
        """.trimIndent()


            mockMvc.perform(post("/todos")
                .content(requestBodyJson)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated) // check status
        }

        @Test
        @DisplayName("POSTリクエストはtodoServiceにcreate()を返す")
        fun `POST endpoint should call todoService create()`() {
            val todoRequest = NewTodoRequest( "Learn Kotlin")
            every { mockTodoService.create(todoRequest)} returns 999

            val requestBodyJson = """
            {
                "text": "${todoRequest.text}"
            }
        """.trimIndent()


            mockMvc.perform(post("/todos")
                .content(requestBodyJson)
                .contentType(APPLICATION_JSON))


            verify { mockTodoService.create(todoRequest) }
        }

        @Test
        @DisplayName ("POSTエンドポイントは新しいIDを返す")
        fun `POST endpoint should return the created ID`() {
            val newId: Long = 9999

            every { mockTodoService.create(any()) } returns newId

            val requestBodyJson = """
            {
                "text": ""
            }
        """.trimIndent()


            val responseBody = mockMvc.perform(post("/todos")
                .content(requestBodyJson)
                .contentType(APPLICATION_JSON))
                .andReturn()
                .response
                .contentAsString


            assertThat(responseBody, equalTo("$newId"))
        }
    }
}
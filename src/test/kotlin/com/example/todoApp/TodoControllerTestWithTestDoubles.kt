package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.testdoubles.SpyStubTodoService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoControllerTestWithTestDoubles {

    lateinit var spyStubTodoService: SpyStubTodoService
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        spyStubTodoService = SpyStubTodoService()
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(spyStubTodoService))
            .build()
    }

    @Nested
    inner class GetTodos {
        @Test
        @DisplayName("GETエンドポイントはOKステータスを返す")
        fun `GET endpoint should return status OK`() {
            mockMvc.perform(get("/todos"))
                .andExpect(status().isOk) // check status
        }

        @Test
        @DisplayName("GETリクエストはtodoObjのリストを返す")
        fun `GET endpoint should return list of Todo`() {
            val todoResponse = TodoResponse(1, "Learn Kotlin")

            // need to set up stub
            spyStubTodoService.stubForTodos(listOf(todoResponse))

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
            mockMvc.perform(get("/todos"))


            assertThat(spyStubTodoService.todosCalled, equalTo(true))
            assertThat(spyStubTodoService.todosCalledTimes, equalTo(1))
        }
    }

    @Nested
    inner class PostTodo {
        @Test
        @DisplayName("POSTエンドポイントはCREATEDステータスを返す")
        fun `POST endpoint should return status CREATED`() {
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

            val requestBodyJson = """
            {
                "text": "${todoRequest.text}"
            }
        """.trimIndent()


            mockMvc.perform(post("/todos")
                .content(requestBodyJson)
                .contentType(APPLICATION_JSON))


            assertThat(spyStubTodoService.createCalled, equalTo(true))
            assertThat(spyStubTodoService.createCalledTimes, equalTo(1))
        }

        @Test
        @DisplayName ("POSTエンドポイントは新しいIDを返す")
        fun `POST endpoint should return the created ID`() {
            val newId: Long = 9999

            // need to set up stub
            spyStubTodoService.stubForCreate(newId)

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
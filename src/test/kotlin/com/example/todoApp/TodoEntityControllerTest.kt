package com.example.todoApp

import com.example.todoApp.dto.TodoResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoEntityControllerTest {

    lateinit var mockTodoRepository: TodoRepository
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockTodoRepository = mockk()
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(mockTodoRepository))
            .build()
    }

    @Nested
    inner class GetTodos {
        @Test
        @DisplayName("GETエンドポイントはOKステータスを返す")
        fun `GET endpoint should return status OK`() {
            every { mockTodoRepository.findAll() } returns listOf()


            mockMvc.perform(get("/todos"))
                .andExpect(status().isOk) // check status
        }

        @Test
        @DisplayName("GETリクエストはtodoObjのリストを返す")
        fun `GET endpoint should return list of Todo`() {
            val todoEntity = TodoEntity(1, "Learn Kotlin")
            every { mockTodoRepository.findAll() } returns listOf(todoEntity)
            val expectedJson = """
            [
                {
                    "id": ${todoEntity.id},
                    "text": "${todoEntity.text}"
                }
            ]
        """.trimIndent()


            mockMvc.perform(get("/todos"))
                .andExpect(content().json(expectedJson))
        }

        @Test
        @DisplayName("GETリクエストはtodoリポジトリからfindAllを返す")
        fun `GET endpoint should call todoRepository findAll()`() {
            every { mockTodoRepository.findAll() } returns listOf() // return type of findAll() is List<TodoEntity>


            mockMvc.perform(get("/todos"))


            verify { mockTodoRepository.findAll() }
        }
    }

    @Nested
    inner class PostTodo {
        @Test
        @DisplayName("POSTエンドポイントはCREATEDステータスを返す")
        fun `POST endpoint should return status CREATED`() {
            every { mockTodoRepository.save(any())} returns TodoEntity(999, "")
            val requestBodyJson = """
            {
                "text": ""
            }
        """.trimIndent()


            mockMvc.perform(post("/todos")
                .content(requestBodyJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated) // check status
        }

        @Test
        @DisplayName("POSTリクエストはtodoリポジトリにsaveを返す")
        fun `POST endpoint should call todoRepository save()`() {
            val todoEntityToSave = TodoEntity(0, "Learn Kotlin")
            every { mockTodoRepository.save(todoEntityToSave)} returns TodoEntity(999, "Learn Kotlin")

            val requestBodyJson = """
            {
                "text": "${todoEntityToSave.text}"
            }
        """.trimIndent()


            mockMvc.perform(post("/todos")
                .content(requestBodyJson)
                .contentType(MediaType.APPLICATION_JSON))


            verify { mockTodoRepository.save(todoEntityToSave) }
        }

        @Test
        @DisplayName ("POSTエンドポイントは新しいIDを返す")
        fun `POST endpoint should return the created ID`() {
            val newId: Long = 9999

            every { mockTodoRepository.save(any()) } returns TodoEntity(newId, "")

            val requestBodyJson = """
            {
                "text": ""
            }
        """.trimIndent()


            val responseBody = mockMvc.perform(post("/todos")
                .content(requestBodyJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response
                .contentAsString


            assertThat(responseBody, equalTo("$newId"))
        }
    }
}
package com.example.todoApp

import com.example.todoApp.dto.TodoResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
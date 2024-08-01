package com.example.todoApp

import com.example.todoApp.dto.TodoResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoEntityControllerTest {
    @Test
    @DisplayName("GETエンドポイントはOKステータスを返す")
    fun `GET endpoint should return status OK`() {

        // new instance of mockMvc using our TodoController
        val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(TodoController())
            .build()

        mockMvc.perform(get("/todos"))
            .andExpect(status().isOk) // check status
    }

    @Test
    @DisplayName("GETリクエストはtodoObjのリストを返す")
    fun `GET endpoint should return list of Todo`() {
        val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(TodoController())
            .build()

        // multi-line string
        val expectedJson = """
            [
                {
                    "id": 1,
                    "text": "Learn Kotlin"
                }
            ]
        """.trimIndent()

        mockMvc.perform(get("/todos"))
            // check if returned JSON string is same as expectedJson
            .andExpect(content().json(expectedJson))
    }

    @Test
    @DisplayName("GETリクエストはtodoリポジトリからfindAllを返す" )
    fun `GET endpoint should call todoRepository findAll()`() {
        val mockTodoRepository :TodoRepository = mockk()
        val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(TodoController())
            .build()
//        val
    }
}
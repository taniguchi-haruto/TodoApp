package com.example.todoApp

import com.example.todoApp.dto.TodoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController {
    @GetMapping("/todos")
    fun getTodos(): List<TodoResponse> {
        val todo = TodoResponse(1, "Learn Kotlin") // const todo = new Todo(1, "Learn Kotlin")
        val list = listOf(todo) // JS: const list = [todo]
        return list
    }
}
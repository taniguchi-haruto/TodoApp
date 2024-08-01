package com.example.todoApp

import com.example.todoApp.dto.TodoResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(val todoRepository: TodoRepository) {

    @GetMapping("/todos")
    fun getTodos(): List<TodoResponse> {
        val todoEntities = todoRepository.findAll() // List<TodoEntity>
        return todoEntities.map {
            TodoResponse(it.id, it.text)
        }
    }
}
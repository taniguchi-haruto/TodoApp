package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(val todoService: TodoService) {

    @GetMapping("/todos")
    fun getTodos(): List<TodoResponse> {
        return todoService.todos() // List<TodoEntity>
    }

//    @PostMapping("/todos")
//    fun postTodos() : ResponseEntity<Unit> {
//        return ResponseEntity.status(HttpStatus.CREATED).build()
//    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    fun postTodo(@RequestBody newTodo: NewTodoRequest) :Long{
        return todoService.create(newTodo)
    }
}
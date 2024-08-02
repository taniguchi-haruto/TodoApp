package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todos")
class TodoController(val todoService: TodoService) {

    @GetMapping
    fun getTodos(): List<TodoResponse> {
        return todoService.todos() // List<TodoEntity>
    }

//    @PostMapping
//    fun postTodos() : ResponseEntity<Unit> {
//        return ResponseEntity.status(HttpStatus.CREATED).build()
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postTodo(@RequestBody newTodo: NewTodoRequest) :Long{
        return todoService.create(newTodo)
    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable id: Long ,@RequestBody updateTodoRequest: UpdateTodoRequest)  {
        todoService.update(id, updateTodoRequest)
    }
}
package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.IllegalStateException

@RestController
@RequestMapping("/todos")
class TodoController(val todoService: TodoService) {

    @GetMapping
    fun getTodos(): List<TodoResponse> {
        return todoService.todos() // List<TodoEntity>
    }

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: Long): TodoResponse {
        return todoService.todo(id)
    }


//    @PostMapping
//    fun postTodos() : ResponseEntity<Unit> {
//        return ResponseEntity.status(HttpStatus.CREATED).build()
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postTodo(@RequestBody newTodo: NewTodoRequest): Long {
        return todoService.create(newTodo)
    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable id: Long, @RequestBody updateTodoRequest: UpdateTodoRequest): TodoResponse {
        return todoService.update(id, updateTodoRequest)

    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): Long {
        return todoService.delete(id)
    }
}
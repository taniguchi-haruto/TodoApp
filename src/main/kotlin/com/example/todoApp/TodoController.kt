package com.example.todoApp


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoAppController (
    @Autowired val todoRepository: TodoRepository
){
    @GetMapping("/todos")
    fun getTodo():List<Todo> {
         return todoRepository.findAll()
    }
    @PostMapping("/todos")
    fun postTodo(@RequestBody todo:Todo){
        todoRepository.save(todo)
    }
}
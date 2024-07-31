package com.example.todoApp


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoAppController {
    @GetMapping("/todos")
    fun getTodo():Array<Todo> {
        return arrayOf(Todo(1, "foo"), Todo(2, "bar"))
    }
}
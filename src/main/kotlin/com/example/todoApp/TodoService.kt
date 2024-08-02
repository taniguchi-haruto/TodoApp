package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse

interface TodoService {

    fun todos(): List<TodoResponse>

    fun create(newTodoRequest: NewTodoRequest): Long
}
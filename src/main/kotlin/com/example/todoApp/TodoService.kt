package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest

interface TodoService {

    fun todos(): List<TodoResponse>

    fun create(newTodoRequest: NewTodoRequest): Long

    fun update(id: Long, updateTodoRequest: UpdateTodoRequest): TodoResponse
}
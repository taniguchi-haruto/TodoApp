package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import org.springframework.stereotype.Service

@Service
class DefaultTodoService(val todoRepository: TodoRepository) : TodoService {
    override fun todos(): List<TodoResponse> {

        val todoEntities = todoRepository.findAll() // List<TodoEntity>
        return todoEntities.map {
            TodoResponse(it.id, it.text)
        }
    }

    override fun create(newTodoRequest: NewTodoRequest): Long {
        val newTodoEntity = todoRepository.save(TodoEntity(text = newTodoRequest.text))

        return newTodoEntity.id
    }
}
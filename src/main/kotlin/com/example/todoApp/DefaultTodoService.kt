package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest
import org.springframework.data.repository.findByIdOrNull
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

    override fun update(id: Long, updateTodoRequest: UpdateTodoRequest): TodoResponse {
        val foundTodo: TodoEntity =
            todoRepository.findByIdOrNull(id) ?: throw RuntimeException("no todo found for id $id")

        val updated = todoRepository.save(TodoEntity(foundTodo.id, updateTodoRequest.text))
        return TodoResponse(updated.id, updated.text)
    }

    override fun delete(id: Long):Long{
        val foundTodo: TodoEntity =
            todoRepository.findByIdOrNull(id) ?: throw RuntimeException("no todo found for id $id")
            todoRepository.delete(TodoEntity(foundTodo.id, foundTodo.text))
        return foundTodo.id
    }

}
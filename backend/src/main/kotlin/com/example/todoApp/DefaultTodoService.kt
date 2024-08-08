package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultTodoService(val todoRepository: TodoRepository) : TodoService {
    override fun todos(): List<TodoResponse> {

        val todoEntities = todoRepository.findAll()
        return todoEntities.map {
            TodoResponse(it.id, it.text)
        }
    }

    override fun todo(id: Long): TodoResponse {
        val found = findTodo(id)
        return TodoResponse(found.id, found.text)
    }

    fun findTodo(id: Long): TodoEntity {
        val found = todoRepository.findByIdOrNull(id) ?: throw TodoNotFoundException("no todo found for id $id")
        return found
    }

    override fun create(newTodoRequest: NewTodoRequest): Long {
        val newTodoEntity = todoRepository.save(TodoEntity(text = newTodoRequest.text))

        return newTodoEntity.id
    }

    override fun update(id: Long, updateTodoRequest: UpdateTodoRequest): TodoResponse {
        val foundTodo = findTodo(id)

        val updated = todoRepository.save(TodoEntity(foundTodo.id, updateTodoRequest.text))
        return TodoResponse(updated.id, updated.text)
    }

    override fun delete(id: Long): Long {
        val foundTodo = findTodo(id)
        todoRepository.deleteById(foundTodo.id)
        return foundTodo.id
    }

}
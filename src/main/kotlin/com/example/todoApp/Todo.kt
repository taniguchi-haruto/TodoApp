package com.example.todoApp

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Todo(
    @Id
    val id: Long,
    val text: String
)

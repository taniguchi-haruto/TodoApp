package com.example.todoApp

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity // this class maps to a table inside database
class TodoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto increment
    val id: Long = 0,
    val text: String
)

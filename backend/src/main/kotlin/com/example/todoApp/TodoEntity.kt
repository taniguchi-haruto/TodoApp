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


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TodoEntity

        if (id != other.id) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}

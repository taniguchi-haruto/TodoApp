package com.example.todoApp.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "TB_USERS")
class UserEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id val id: Long,

    @Column(name = "username")
    val name: String,
    val email: String


    ) {
}
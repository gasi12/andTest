package com.example.andtest.api.dto

data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val role: String, //ADMIN or USER
    val email: String
)

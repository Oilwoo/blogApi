package com.example.urssublogapi.dto

data class UserRequest(
    val email: String,
    val password: String,
    val username: String?
)
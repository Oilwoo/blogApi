package com.example.urssublogapi.dto

data class CommentRequest(
    val commentId: Long,
    val articleId: Long,
    val email: String,
    val password: String,
    val content: String?,
)

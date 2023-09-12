package com.example.urssublogapi.dto

data class ArticleRequest(
    val articleId: Long?,
    val email: String,
    val password: String,
    val content: String?,
    val title: String?
)

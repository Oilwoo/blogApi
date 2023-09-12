package com.example.urssublogapi.service

import com.example.urssublogapi.model.Article
import com.example.urssublogapi.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleService(@Autowired val articleRepository: ArticleRepository){

    fun findArticleById(id: Long): Article? = articleRepository.findById(id).orElse(null)

    fun createArticle(article: Article): Article {
        val currentTime = LocalDateTime.now()
        val newArticle = article.copy(
            createdAt = currentTime,
            updatedAt = currentTime
        )
        return articleRepository.save(newArticle)
    }
    fun updateArticle(article: Article): Article {
        val currentTime = LocalDateTime.now()
        val newArticle = article.copy(
            updatedAt = currentTime
        )
        return articleRepository.save(newArticle)
    }

    fun deleteArticle(id: Long): Boolean {
        val comment = findArticleById(id) ?: return false
        articleRepository.delete(comment)
        return true
    }

    fun deleteArticleByUserId(userId: Long) {
        articleRepository.deleteByUserId(userId)
    }
}

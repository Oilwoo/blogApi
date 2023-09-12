package com.example.urssublogapi.repository

import com.example.urssublogapi.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ArticleRepository : JpaRepository<Article, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a.user.id = :userId")
    fun deleteByUserId(@Param("userId") userId: Long)
}
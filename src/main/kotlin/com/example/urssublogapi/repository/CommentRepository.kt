package com.example.urssublogapi.repository

import com.example.urssublogapi.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface CommentRepository : JpaRepository<Comment, Long>{
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.user.id = :userId")
    @Modifying
    fun deleteByUserId(@Param("userId") userId: Long)

    @Transactional
    @Query("DELETE FROM Comment c WHERE c.article.id = :articleId")
    @Modifying
    fun deleteByArticleId(@Param("articleId") articleId: Long)
}

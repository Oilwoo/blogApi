package com.example.urssublogapi.service

import com.example.urssublogapi.model.Comment
import com.example.urssublogapi.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentService(
    @Autowired private val commentRepository: CommentRepository
) {
    fun createComment(comment: Comment): Comment {
        val currentTime = LocalDateTime.now()
        comment.createdAt = currentTime
        comment.updatedAt = currentTime
        return commentRepository.save(comment)
    }

    fun findCommentById(id: Long): Comment? {
        return commentRepository.findById(id).orElse(null)
    }

    fun updateComment(id: Long, newContent: String): Comment? {
        val comment = findCommentById(id) ?: return null
        comment.content = newContent
        comment.updatedAt = LocalDateTime.now()
        return commentRepository.save(comment)
    }

    fun deleteComment(id: Long): Boolean {
        val comment = findCommentById(id) ?: return false
        commentRepository.delete(comment)
        return true
    }

    fun deleteCommentByArticleId(articleId: Long) {
        commentRepository.deleteByArticleId(articleId)
    }

    fun deleteCommentByUserId(userId: Long) {
        commentRepository.deleteByUserId(userId)
    }

}


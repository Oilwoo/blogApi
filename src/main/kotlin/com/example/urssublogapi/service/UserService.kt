package com.example.urssublogapi.service
// src/main/kotlin/com/example/demo/service/UserService.kt

import com.example.urssublogapi.model.User
import com.example.urssublogapi.repository.ArticleRepository
import com.example.urssublogapi.repository.CommentRepository
import com.example.urssublogapi.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncoder: BCryptPasswordEncoder,
                  private val articleRepository: ArticleRepository,
                  private val commentRepository: CommentRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun findUserById(id: Long): User? = userRepository.findById(id).orElse(null)

    fun findUserByEmailAndPassword(email: String, rawPassword: String): User? {
        val user = userRepository.findByEmail(email) ?: return null

        if (passwordEncoder.matches(rawPassword, user.password)) {
            return user
        }

        return null
    }

    fun createUser(user: User): User {
        val encryptedPassword = passwordEncoder.encode(user.password)
        val currentTime = Date()
        val newUser = user.copy(
            password = encryptedPassword,
            createdAt = currentTime,
            updatedAt = currentTime
        )
        return userRepository.save(newUser)
    }

    fun updateUser(id: Long, newUser: User): User? {
        val existingUser = findUserById(id) ?: return null
        val currentTime = Date()
        val updatedUser = existingUser.copy(
                email = newUser.email,
                password = newUser.password,
                username = newUser.username,
                updatedAt = currentTime
        )
        return userRepository.save(updatedUser)
    }

    @Transactional
    fun deleteUser(id: Long){
        // delete all associated comments first
        commentRepository.deleteByUserId(id)

        // then delete the article
        articleRepository.deleteByUserId(id)

        // then delete the user
        userRepository.deleteById(id)
    }
}

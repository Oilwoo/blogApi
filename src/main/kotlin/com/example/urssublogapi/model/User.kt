package com.example.urssublogapi.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        val userId: Long? = null,

        @Column(name = "created_at")
        val createdAt: Date? = null,

        @Column(name = "updated_at")
        val updatedAt: Date? = null,

        @Column(name = "email")
        val email: String,

        @Column(name = "password")
        val password: String,

        @Column(name = "username")
        val username: String
) {
    constructor() : this(null, null, null, "", "", "")
}

package com.example.urssublogapi.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "article")
data class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    var articleId: Long? = null,

    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var content: String? = null,
    var title: String? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    var user: User? = null

)

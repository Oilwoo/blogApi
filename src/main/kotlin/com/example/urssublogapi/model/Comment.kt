package com.example.urssublogapi.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "comment")
data class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Long? = null,

    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var content: String? = null,

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    var article: Article? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    var user: User? = null
) {
    constructor() : this(null, null, null, null, null)
}

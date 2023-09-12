package com.example.urssublogapi.controller

import com.example.urssublogapi.dto.*
import com.example.urssublogapi.model.Article
import com.example.urssublogapi.model.Comment
import com.example.urssublogapi.model.User
import com.example.urssublogapi.service.ArticleService
import com.example.urssublogapi.service.CommentService
import com.example.urssublogapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class APiController(
    @Autowired val userService: UserService,
    @Autowired val articleService: ArticleService,
    @Autowired val commentService: CommentService,
) {
    //회원가입 API_001
    @PostMapping("/user/create")
    fun createUser(
        @RequestBody request: UserRequest
    ): UserResponse {
        val user = User(
            email = request.email,
            password = request.password,
            username = request.username!!
        )
        val createdUser = userService.createUser(user)
        return UserResponse(
            email = createdUser.email,
            username = createdUser.username
        )
    }

    //게시글 작성하기 API_002
    @PostMapping("/article/create")
    fun createArticle(
        @RequestBody request: ArticleRequest
    ): ArticleResponse {
        // 널 체크 및 공백 체크
        if (request.title.isNullOrEmpty() || request.content.isNullOrEmpty()) {
            throw IllegalArgumentException("Title and Content must not be null or empty.")
        }

        // 유저체크
        var user = userService.findUserByEmailAndPassword(request.email, request.password) ?: throw IllegalArgumentException("User not found.")

        var article = Article(
            user = user,
            title = request.title,
            content = request.content
        )

        var newArticle = articleService.createArticle(article)

        return ArticleResponse(
            articleId = newArticle.articleId,
            email = newArticle.user?.email,
            title = newArticle.title,
            content = newArticle.content
        )
    }

    //게시글 수정하기 API_003
    @PostMapping("/article/modify")
    fun modifyArticle(
        @RequestBody request: ArticleRequest
    ): ArticleResponse {
        // 널 체크 및 공백 체크
        if (request.title.isNullOrEmpty() || request.content.isNullOrEmpty()) {
            throw IllegalArgumentException("Title and Content must not be null or empty.")
        }

        // 유저체크
        var user = userService.findUserByEmailAndPassword(request.email, request.password) ?: throw IllegalArgumentException("User not found.")

        // 게시글 체크
        var article = articleService.findArticleById(request.articleId!!) ?: throw IllegalArgumentException("Article not found.")

        //수정
        article.user = user
        article.title = request.title
        article.content = request.content

        var newArticle = articleService.updateArticle(article)

        return ArticleResponse(
            articleId = newArticle.articleId,
            email = newArticle.user?.email,
            title = newArticle.title,
            content = newArticle.content
        )
    }

    //댓글 작성하기 API_004
    @PostMapping("/comment/create")
    fun createComment(
        @RequestBody request: CommentRequest
    ): CommentResponse {
        // 널 체크 및 공백 체크
        if (request.content.isNullOrEmpty()) {
            throw IllegalArgumentException("Content must not be null or empty.")
        }

        // 유저체크
        var user = userService.findUserByEmailAndPassword(request.email, request.password) ?: throw IllegalArgumentException("User not found.")

        // 게시글 체크
        var article = articleService.findArticleById(request.articleId!!) ?: throw IllegalArgumentException("Article not found.")

        var comment = Comment(
            user = user,
            article = article,
            content = request.content
        )

        var newComment = commentService.createComment(comment)

        return CommentResponse(
            commentId = newComment.commentId!!,
            email = newComment.user?.email!!,
            content = newComment.content!!
        )
    }

    //댓글 수정하기 API_005
    @PostMapping("/comment/modify")
    fun modifyComment(
        @RequestBody request: CommentRequest
    ): CommentResponse {
        // 널 체크 및 공백 체크
        if (request.content.isNullOrEmpty()) {
            throw IllegalArgumentException("Content must not be null or empty.")
        }

        // 유저체크
        val user = userService.findUserByEmailAndPassword(request.email, request.password) ?: throw IllegalArgumentException("User not found.")

        // 게시글 체크
        articleService.findArticleById(request.articleId!!) ?: throw IllegalArgumentException("Article not found.")

        // 댓글 체크
        val comment = commentService.findCommentById(request.commentId!!) ?: throw IllegalArgumentException("Comment not found.")

        // 유저가 댓글 작성자인지 확인
        if (comment.user?.userId != user.userId) {
            throw IllegalArgumentException("Unauthorized.")
        }

        // 댓글 id만 받아와도 수정 가능
        var newComment = commentService.updateComment(request.commentId, request.content)

        return CommentResponse(
            commentId = newComment!!.commentId,
            email = newComment.user?.email,
            content = newComment.content
        )
    }

    //댓글 삭제하기 API_006
    @PostMapping("/comment/delete")
    fun deleteComment(
        @RequestBody request: CommentRequest
    ): ResponseEntity<Void> {
        // 유저체크
        val user = userService.findUserByEmailAndPassword(request.email, request.password) ?: throw IllegalArgumentException("User not found.")

        //게시글 체크
        articleService.findArticleById(request.articleId!!) ?: throw IllegalArgumentException("Article not found.")

        // 댓글 체크
        val comment = commentService.findCommentById(request.commentId!!) ?: throw IllegalArgumentException("Comment not found.")

        // 유저가 댓글 작성자인지 확인
        if (comment.user?.userId != user.userId) {
            throw IllegalArgumentException("Unauthorized.")
        }

        // 댓글 삭제
        commentService.deleteComment(request.commentId)

        return ResponseEntity.ok().build()

    }

    //게시글 삭제하기 API_007
    @PostMapping("/article/delete")
    fun deleteArticle(@RequestBody request: ArticleRequest): Map<String, String> {
        // 유저체크
        val user = userService.findUserByEmailAndPassword(request.email, request.password)
            ?: throw IllegalArgumentException("User not found.")

        // 게시글 체크
        val article = articleService.findArticleById(request.articleId!!)
            ?: throw IllegalArgumentException("Article not found.")

        // 유저가 게시글 작성자인지 확인
        if (article.user?.userId != user.userId) {
            throw IllegalArgumentException("Unauthorized.")
        }

        // 게시글과 관련된 댓글 삭제
        commentService.deleteCommentByArticleId(request.articleId)

        // 게시글 삭제
        articleService.deleteArticle(request.articleId)

        return mapOf(
            "email" to user.email,
            "password" to user.password
        )
    }

    //회원 탈퇴 API_008
    @PostMapping("/user/delete")
    fun deleteUser(@RequestBody request: UserRequest): Map<String, String> {
        // 유저체크
        val user = userService.findUserByEmailAndPassword(request.email, request.password)
            ?: throw IllegalArgumentException("User not found.")

        // 유저 삭제
        userService.deleteUser(user.userId!!)

        return mapOf(
            "email" to user.email,
            "password" to user.password
        )
    }

}
package com.example.urssublogapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleBadRequest(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<CustomErrorResponse> {
        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.localizedMessage ?: "잘못된 요청입니다.",
                request.getDescription(false)
        )
    }

    @ExceptionHandler(value = [NullPointerException::class])
    fun handleNotFound(ex: NullPointerException, request: WebRequest): ResponseEntity<CustomErrorResponse> {
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.localizedMessage ?: "찾을 수 없습니다.",
                request.getDescription(false)
        )
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleGeneralException(ex: Exception, request: WebRequest): ResponseEntity<CustomErrorResponse> {
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.localizedMessage ?: "서버 내부 오류입니다.",
                request.getDescription(false)
        )
    }

    private fun createErrorResponse(status: HttpStatus, message: String, uri: String): ResponseEntity<CustomErrorResponse> {
        val errorDetails = CustomErrorResponse(
                time = LocalDateTime.now(),
                status = status.name,
                message = message,
                requestURI = uri
        )
        return ResponseEntity(errorDetails, status)
    }
}

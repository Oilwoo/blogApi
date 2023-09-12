package com.example.urssublogapi.exception

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CustomErrorResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        val time: LocalDateTime,
        val status: String,
        val message: String,
        val requestURI: String
)
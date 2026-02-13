package com.julian.project.dto

import java.time.LocalDateTime

data class ChatResponse(
    val threadId: Long,
    val question: String,
    val answer: String,
    val createdAt: LocalDateTime
)

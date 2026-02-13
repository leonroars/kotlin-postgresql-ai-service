package com.julian.project.dto

import java.time.LocalDateTime

data class ThreadResponse(
    val threadId: Long,
    val chats: List<ChatResponse>,
    val createdAt: LocalDateTime
)

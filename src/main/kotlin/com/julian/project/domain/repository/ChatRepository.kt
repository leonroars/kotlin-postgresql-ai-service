package com.julian.project.domain.repository

import com.julian.project.domain.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, Long> {

    // 스레드 내 대화 히스토리 (OpenAI API 요청용)
    fun findByThreadIdOrderByCreatedAt(threadId: Long) : List<Chat>
}

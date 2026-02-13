package com.julian.project.service

import com.julian.project.common.exceptions.UnavailableRequestException
import com.julian.project.domain.*
import com.julian.project.domain.repository.ChatRepository
import com.julian.project.domain.repository.ThreadRepository
import com.julian.project.domain.repository.UserRepository
import com.julian.project.dto.ChatCreateRequest
import com.julian.project.dto.ChatResponse
import com.julian.project.dto.ThreadResponse
import com.julian.project.infrastructure.ai.AiClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ChatService(
    private val userRepository: UserRepository,
    private val threadRepository: ThreadRepository,
    private val chatRepository: ChatRepository,
    private val aiClient: AiClient
) {
    @Transactional
    fun createChat(userId: Long, request: ChatCreateRequest): ChatResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { UnavailableRequestException("사용자를 찾을 수 없습니다.") }

        val thread = getOrCreateThread(user)

        // 기존 대화 히스토리 조회 및 OpenAI messages 형식으로 변환
        val chatHistory = chatRepository.findByThreadIdOrderByCreatedAtAsc(thread.id!!)
        val messages = buildMessages(chatHistory, request.question)

        // AI 응답 생성 (실패 시 fallback)
        val answer = try {
            aiClient.chat(messages)
        } catch (e: Exception) {
            "AI 응답을 생성할 수 없습니다."
        }

        val chat = Chat(
            thread = thread,
            question = request.question,
            answer = answer
        )
        chatRepository.save(chat)

        thread.updatedAt = LocalDateTime.now()
        threadRepository.save(thread)

        return ChatResponse(
            threadId = thread.id!!,
            question = chat.question,
            answer = chat.answer,
            createdAt = chat.createdAt
        )
    }

    @Transactional(readOnly = true)
    fun getChats(userId: Long, pageable: Pageable): Page<ThreadResponse> {
        val user = userRepository.findById(userId)
            .orElseThrow { UnavailableRequestException("사용자를 찾을 수 없습니다.") }

        val threads = if (user.authority == Authority.ADMIN) {
            threadRepository.findAll(pageable)
        } else {
            threadRepository.findAllByUserId(userId, pageable)
        }

        return threads.map { thread ->
            val chats = chatRepository.findByThreadIdOrderByCreatedAtAsc(thread.id!!)
            ThreadResponse(
                threadId = thread.id!!,
                chats = chats.map { ChatResponse(
                    threadId = thread.id!!,
                    question = it.question,
                    answer = it.answer,
                    createdAt = it.createdAt
                )},
                createdAt = thread.createdAt
            )
        }
    }

    private fun getOrCreateThread(user: User): Thread {
        val latest = threadRepository.findTopByUserIdOrderByUpdatedAtDesc(user.id!!)

        if (latest == null || latest.updatedAt.plusMinutes(30).isBefore(LocalDateTime.now())) {
            return threadRepository.save(Thread(user = user))
        }
        return latest
    }

    private fun buildMessages(chatHistory: List<Chat>, newQuestion: String): List<Map<String, String>> {
        val messages = mutableListOf<Map<String, String>>()

        for (chat in chatHistory) {
            messages.add(mapOf("role" to "user", "content" to chat.question))
            messages.add(mapOf("role" to "assistant", "content" to chat.answer))
        }

        messages.add(mapOf("role" to "user", "content" to newQuestion))

        return messages
    }
}

package com.julian.project.controller

import com.julian.project.dto.ChatCreateRequest
import com.julian.project.dto.ChatResponse
import com.julian.project.dto.ThreadResponse
import com.julian.project.service.ChatService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/chats")
class ChatController(
    private val chatService: ChatService
) {

    /**
     * 대화 생성
     */
    @PostMapping
    fun createChat(
        @RequestParam userId: Long,
        @RequestBody request: ChatCreateRequest
    ): ResponseEntity<ChatResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(userId, request))
    }

    /**
     * 대화 목록 조회 :
     */
    @GetMapping
    fun getChats(
        @RequestParam userId: Long,
        pageable: Pageable
    ): ResponseEntity<Page<ThreadResponse>> {
        return ResponseEntity.ok(chatService.getChats(userId, pageable))
    }
}

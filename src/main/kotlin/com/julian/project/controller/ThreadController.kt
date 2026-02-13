package com.julian.project.controller

import com.julian.project.service.ThreadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/threads")
class ThreadController(
    private val threadService: ThreadService
) {
    @DeleteMapping("/{threadId}")
    fun deleteThread(
        @PathVariable threadId: Long,
        @RequestParam userId: Long
    ): ResponseEntity<Void>
    {
        threadService.delete(userId, threadId)
        return ResponseEntity.noContent().build()
    }
}

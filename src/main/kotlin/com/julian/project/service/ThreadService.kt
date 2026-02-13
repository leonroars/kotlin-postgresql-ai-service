package com.julian.project.service

import com.julian.project.common.exceptions.BusinessRuleViolationException
import com.julian.project.common.exceptions.UnavailableRequestException
import com.julian.project.domain.Authority
import com.julian.project.domain.repository.ThreadRepository
import com.julian.project.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ThreadService(
    private val userRepository: UserRepository,
    private val threadRepository: ThreadRepository
) {
    @Transactional
    fun delete(userId: Long, threadId: Long) {
        val user = userRepository.findById(userId)
            .orElseThrow { UnavailableRequestException("사용자를 찾을 수 없습니다.") }

        val thread = threadRepository.findById(threadId)
            .orElseThrow { UnavailableRequestException("스레드를 찾을 수 없습니다.") }

        if (user.authority != Authority.ADMIN && thread.user.id != userId) {
            throw BusinessRuleViolationException("자신의 스레드만 삭제할 수 있습니다.")
        }

        threadRepository.delete(thread)
    }
}

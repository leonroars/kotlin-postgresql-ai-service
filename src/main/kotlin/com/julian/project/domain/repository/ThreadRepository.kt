package com.julian.project.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import com.julian.project.domain.Thread

interface ThreadRepository : JpaRepository<Thread, Long> {

    // 사용자 ID 로 전체 조회
    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Thread>

    // 스레드 판단용 (30분 규칙)
    fun findTopByUserIdOrderByUpdatedAtDesc(userId: Long): Thread?

    // 스레드 단위 대화 목록 조회 (페이지네이션 적용 필요)
    fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable) : Page<Thread>

    // 관리자용 전체 조회
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable) : Page<Thread>
}

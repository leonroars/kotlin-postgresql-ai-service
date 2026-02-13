package com.julian.project.domain.repository

import com.julian.project.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface UserRepository : JpaRepository<User, Long> {

    // 이메일로 사용자 조회. 이메일 PK 이므로 단건 or null
    fun findByEmail(email: String): User?

    // 생성일자로 사용자 목록 조회 - 추후 관리자를 위한 '사용자 활동기록 요청' 시 사용될 수 있음.
    fun findAllByCreatedAt(createdAt: LocalDateTime): List<User>
}

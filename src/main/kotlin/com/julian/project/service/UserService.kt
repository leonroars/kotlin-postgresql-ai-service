package com.julian.project.service

import com.julian.project.common.exceptions.BusinessRuleViolationException
import com.julian.project.common.exceptions.UnavailableRequestException
import com.julian.project.domain.Authority
import com.julian.project.domain.User
import com.julian.project.domain.repository.UserRepository
import com.julian.project.dto.LoginRequest
import com.julian.project.dto.SignupRequest
import com.julian.project.dto.UserResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun signUp(request: SignupRequest, authority: Authority): UserResponse {
        // 이메일 중복 체크
        userRepository.findByEmail(request.email)?.let {
            throw BusinessRuleViolationException("이미 사용 중인 이메일입니다.")
        }

        val user = User(
            email = request.email,
            password = request.password,
            name = request.username,
            authority = authority
        )
        val saved = userRepository.save(user)

        return UserResponse(
            id = saved.id!!,
            email = saved.email,
            username = saved.name,
            authority = saved.authority
        )
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): UserResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UnavailableRequestException("사용자를 찾을 수 없습니다.")

        if (user.password != request.password) {
            throw BusinessRuleViolationException("비밀번호가 일치하지 않습니다.")
        }

        return UserResponse(
            id = user.id!!,
            email = user.email,
            username = user.name,
            authority = user.authority
        )
    }
}

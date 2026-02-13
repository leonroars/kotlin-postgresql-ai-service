package com.julian.project.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignupRequest (

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message = "유효한 이메일 주소를 입력해주세요.")
    val email: String,

    @field:NotBlank(message = "패스워드는 필수입니다.")
    val password: String,

    @field:NotBlank(message = "사용자 이름은 필수입니다.")
    val username: String
) {
}

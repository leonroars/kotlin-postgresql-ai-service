package com.julian.project.dto

import com.julian.project.domain.Authority

data class UserResponse (
    val id: Long,
    val email: String,
    val username: String,
    val authority: Authority
)

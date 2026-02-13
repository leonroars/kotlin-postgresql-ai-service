package com.julian.project.controller

import com.julian.project.domain.Authority
import com.julian.project.dto.LoginRequest
import com.julian.project.dto.SignupRequest
import com.julian.project.dto.UserResponse
import com.julian.project.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController (
    private val userService: UserService
){

    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody request: SignupRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request, Authority.MEMBER));
    }

    @PostMapping("/admin/signup")
    fun adminSignUp(@Valid @RequestBody request: SignupRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request, Authority.ADMIN))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.login(request));
    }

}

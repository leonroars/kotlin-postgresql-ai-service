package com.julian.project.common

import com.julian.project.common.exceptions.BusinessRuleViolationException
import com.julian.project.common.exceptions.UnavailableRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessRuleViolationException::class)
    fun handleNotFound(e: BusinessRuleViolationException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf("error" to e.message))

    @ExceptionHandler(UnavailableRequestException::class)
    fun handleUnavailable(e: UnavailableRequestException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to e.message))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to e.bindingResult.fieldErrors.joinToString(", ") {
                "${it.field}: ${it.defaultMessage}"
            }))

    @ExceptionHandler(Exception::class)
    fun handleGeneral(e: Exception) =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapOf("error" to "서버 오류가 발생했습니다."))
}
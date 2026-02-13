package com.julian.project.infrastructure.ai

interface AiClient {
    fun chat(messages: List<Map<String, String>>): String
}

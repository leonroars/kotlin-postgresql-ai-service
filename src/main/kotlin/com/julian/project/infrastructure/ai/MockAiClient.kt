package com.julian.project.infrastructure.ai

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!prod")
class MockAiClient : AiClient {

    override fun chat(messages: List<Map<String, String>>): String {
        val lastContent = messages.lastOrNull()?.get("content") ?: ""
        return "Mock AI 답변: $lastContent"
    }
}

package com.julian.project.infrastructure.ai

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ai")
data class AiProperties(
    val openai: OpenAiProperties = OpenAiProperties()
) {
    data class OpenAiProperties(
        val apiKey: String = "",
        val model: String = "gpt-4o-mini",
        val baseUrl: String = "https://api.openai.com/v1"
    )
}

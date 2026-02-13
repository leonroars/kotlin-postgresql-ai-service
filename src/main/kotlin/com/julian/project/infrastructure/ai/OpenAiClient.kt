package com.julian.project.infrastructure.ai

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
@Profile("prod")
class OpenAiClient(
    private val aiProperties: AiProperties,
    private val objectMapper: ObjectMapper
) : AiClient {

    private val restClient: RestClient = RestClient.builder()
        .baseUrl(aiProperties.openai.baseUrl)
        .defaultHeader("Authorization", "Bearer ${aiProperties.openai.apiKey}")
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .build()

    override fun chat(messages: List<Map<String, String>>): String {
        val systemMessage = mapOf("role" to "system", "content" to "당신은 친절한 AI 어시스턴트입니다.")
        val allMessages = listOf(systemMessage) + messages

        val requestBody = mapOf(
            "model" to aiProperties.openai.model,
            "messages" to allMessages,
            "temperature" to 0.7
        )

        val responseJson = restClient.post()
            .uri("/chat/completions")
            .body(objectMapper.writeValueAsString(requestBody))
            .retrieve()
            .body(String::class.java)
            ?: throw RuntimeException("OpenAI API 응답이 비어있습니다.")

        val responseMap = objectMapper.readValue(responseJson, Map::class.java)
        val choices = responseMap["choices"] as? List<*>
            ?: throw RuntimeException("OpenAI API 응답에 choices가 없습니다.")

        val firstChoice = choices.firstOrNull() as? Map<*, *>
            ?: throw RuntimeException("OpenAI API 응답에 choice가 없습니다.")

        val message = firstChoice["message"] as? Map<*, *>
            ?: throw RuntimeException("OpenAI API 응답에 message가 없습니다.")

        return message["content"] as? String
            ?: throw RuntimeException("OpenAI API 응답에 content가 없습니다.")
    }
}

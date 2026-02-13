package com.julian.project

import com.julian.project.infrastructure.ai.AiProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AiProperties::class)
class ProjectApplication

fun main(args: Array<String>) {
	runApplication<ProjectApplication>(*args)
}

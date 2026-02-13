package com.julian.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chats")
class Chat(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    val thread: Thread,

    @Column(nullable = false, columnDefinition = "TEXT")
    val question: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val answer: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

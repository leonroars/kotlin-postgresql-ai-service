package com.julian.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "threads")
class Thread(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "thread", cascade = [CascadeType.ALL])
    val chats: MutableList<Chat> = mutableListOf()
)

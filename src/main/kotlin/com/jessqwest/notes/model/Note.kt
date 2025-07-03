package com.jessqwest.notes.model

import jakarta.persistence.*
import java.util.*

@Entity
data class Note(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,

        val title: String = "",

        @Column(columnDefinition = "TEXT")
        val content: String = ""
)

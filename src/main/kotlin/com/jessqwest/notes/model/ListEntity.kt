package com.jessqwest.notes.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "lists")
data class ListEntity(
        @Id
        val id: String,

        @Column(nullable = true)
        val itemorder: Int? = null,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val type: String, // todo or stock
) {
    constructor() : this(
            id = "",
            name = "",
            type = ""
    )
}
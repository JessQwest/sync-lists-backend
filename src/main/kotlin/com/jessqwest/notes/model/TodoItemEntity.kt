package com.jessqwest.notes.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "todo_items")
data class TodoItemEntity (
        @Id
        val id: String,

        @Column(nullable = true)
        val itemorder: Int? = null,

        @Column(nullable = false)
        val listId: String,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val checked: Boolean,
) {
    constructor() : this(
            id = "",
            listId = "",
            name = "",
            checked = false
    )
}
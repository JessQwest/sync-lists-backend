package com.jessqwest.notes.model

import jakarta.persistence.*

@Entity
@Table(name = "lists")
data class ListEntity(
        @Id
        val id: String,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val type: String // todo or stock
) {
    constructor() : this(
        id = "",
        name = "",
        type = ""
    )
}


@Entity
@Table(name = "todo_items")
data class TodoItemEntity(
        @Id
        val id: String,

        @Column(nullable = false)
        val listId: String, // manual relation

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val checked: Boolean
) {
    constructor() : this(
        id = "",
        listId = "",
        name = "",
        checked = false
    )
}

@Entity
@Table(name = "stock_items")
data class StockItemEntity(
        @Id
        val id: String,

        @Column(nullable = false)
        val listId: String,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val count: Int,

        val flagged: Boolean? = null
) {
    constructor() : this(
        id = "",
        listId = "",
        name = "",
        count = 0,
        flagged = null) {
    }
}


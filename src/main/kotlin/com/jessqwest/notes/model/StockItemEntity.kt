package com.jessqwest.notes.model

import jakarta.persistence.*

@Entity
@Table(name = "stock_items")
data class StockItemEntity(
        @Id
        val id: String,

        @Column(nullable = true)
        val itemorder: Int? = null,

        @Column(nullable = false)
        val listId: String,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val count: Float,

        val flagged: Boolean? = null,
) {
    constructor() : this(
        id = "",
        listId = "",
        name = "",
        count = 0f,
        flagged = null) {
    }
}


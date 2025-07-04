package com.jessqwest.notes.dto

data class ListResponse(
        val id: String,
        val name: String,
        val type: String,
        val items: List<Map<String, Any?>>
)

data class CreateListRequest(
        val name: String,
        val type: String // 'todo' or 'stock'
)
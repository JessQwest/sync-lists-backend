package com.jessqwest.notes.repository

import com.jessqwest.notes.model.ListEntity
import com.jessqwest.notes.model.StockItemEntity
import com.jessqwest.notes.model.TodoItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ListRepository : JpaRepository<ListEntity, String>

@Repository
interface TodoItemRepository : JpaRepository<TodoItemEntity, String> {
    fun findByListId(listId: String): List<TodoItemEntity>
}

@Repository
interface StockItemRepository : JpaRepository<StockItemEntity, String> {
    fun findByListId(listId: String): List<StockItemEntity>
}

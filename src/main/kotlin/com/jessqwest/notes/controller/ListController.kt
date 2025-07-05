package com.jessqwest.notes.controller

import com.jessqwest.notes.dto.CreateListRequest
import com.jessqwest.notes.dto.ListResponse
import com.jessqwest.notes.model.ListEntity
import com.jessqwest.notes.model.StockItemEntity
import com.jessqwest.notes.model.TodoItemEntity
import com.jessqwest.notes.repository.ListRepository
import com.jessqwest.notes.repository.StockItemRepository
import com.jessqwest.notes.repository.TodoItemRepository
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/lists")
@CrossOrigin(origins = ["*"])
class ListController(
        private val listRepo: ListRepository,
        private val todoRepo: TodoItemRepository,
        private val stockRepo: StockItemRepository
) {

    @GetMapping
    fun getAllLists(): List<ListResponse> {
        return listRepo.findAll().map { buildListResponse(it) }
    }

    @GetMapping("/{id}")
    fun getList(@PathVariable id: String): ListResponse {
        val list = listRepo.findById(id).orElseThrow { NoSuchElementException("List not found") }
        return buildListResponse(list)
    }

    @PostMapping
    fun createList(@RequestBody request: CreateListRequest): ListResponse {
        val id = UUID.randomUUID().toString()
        val entity = ListEntity(id, request.name, request.type)
        listRepo.save(entity)
        return buildListResponse(entity)
    }

    @DeleteMapping("/{id}")
    fun deleteList(@PathVariable id: String) {
        todoRepo.findByListId(id).forEach { todoRepo.delete(it) }
        stockRepo.findByListId(id).forEach { stockRepo.delete(it) }
        listRepo.deleteById(id)
    }

    @PostMapping("/{id}/items")
    fun addItem(@PathVariable id: String, @RequestBody item: Map<String, Any>) {
        val list = listRepo.findById(id).orElseThrow { NoSuchElementException("List not found") }

        when (list.type) {
            "todo" -> {
                val entity = TodoItemEntity(
                        id = UUID.randomUUID().toString(),
                        listId = id,
                        name = item["name"] as String,
                        checked = item["checked"] as Boolean
                )
                todoRepo.save(entity)
            }

            "stock" -> {
                val entity = StockItemEntity(
                        id = UUID.randomUUID().toString(),
                        listId = id,
                        name = item["name"] as String,
                        count = (item["count"] as Number).toInt(),
                        flagged = item["flagged"] as? Boolean
                )
                stockRepo.save(entity)
            }

            else -> throw IllegalArgumentException("Invalid list type")
        }
    }

    @PutMapping("/{id}/items/{itemId}")
    fun updateItem(
            @PathVariable id: String,
            @PathVariable itemId: String,
            @RequestBody item: Map<String, Any>
    ) {
        val list = listRepo.findById(id).orElseThrow { NoSuchElementException("List not found") }

        when (list.type) {
            "todo" -> {
                val existing = todoRepo.findById(itemId).orElseThrow()
                val updated = existing.copy(
                        name = item["name"] as String,
                        checked = item["checked"] as Boolean
                )
                todoRepo.save(updated)
            }

            "stock" -> {
                val existing = stockRepo.findById(itemId).orElseThrow()
                val updated = existing.copy(
                        name = item["name"] as String,
                        count = (item["count"] as Number).toInt(),
                        flagged = item["flagged"] as? Boolean
                )
                stockRepo.save(updated)
            }

            else -> throw IllegalArgumentException("Invalid list type")
        }
    }

    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteItem(@PathVariable id: String, @PathVariable itemId: String) {
        val list = listRepo.findById(id).orElseThrow { NoSuchElementException("List not found") }
        when (list.type) {
            "todo" -> todoRepo.deleteById(itemId)
            "stock" -> stockRepo.deleteById(itemId)
        }
    }

    private fun buildListResponse(list: ListEntity): ListResponse {
        val items = when (list.type) {
            "todo" -> todoRepo.findByListId(list.id).map {
                mapOf(
                        "id" to it.id,
                        "name" to it.name,
                        "checked" to it.checked
                )
            }

            "stock" -> stockRepo.findByListId(list.id).map {
                mapOf(
                        "id" to it.id,
                        "name" to it.name,
                        "count" to it.count,
                        "flagged" to it.flagged
                )
            }

            else -> emptyList()
        }

        return ListResponse(list.id, list.name, list.type, items)
    }
}
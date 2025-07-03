package com.jessqwest.notes.controller

import com.jessqwest.notes.model.Note
import com.jessqwest.notes.repository.NoteRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["*"]) // Adjust for security in production
@RestController
@RequestMapping("/notes")
class NoteController(private val repository: NoteRepository) {

    @GetMapping
    fun getAll(): List<Note> = repository.findAll()

    @PostMapping
    fun create(@RequestBody note: Note): Note = repository.save(note)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = repository.deleteById(id)
}

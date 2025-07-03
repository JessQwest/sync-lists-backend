package com.jessqwest.notes.repository

import com.jessqwest.notes.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NoteRepository : JpaRepository<Note, UUID>

package com.losev.myapp.domain.repository

import com.losev.myapp.domain.model.Note

interface NoteRepository {
    fun getNotes(): List<Note>
}
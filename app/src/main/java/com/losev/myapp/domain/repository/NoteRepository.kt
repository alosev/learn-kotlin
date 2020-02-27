package com.losev.myapp.domain.repository

import androidx.lifecycle.LiveData
import com.losev.myapp.domain.model.Note

interface NoteRepository {
    fun getNotes(): LiveData<List<Note>>
    fun saveNote(note: Note)
}
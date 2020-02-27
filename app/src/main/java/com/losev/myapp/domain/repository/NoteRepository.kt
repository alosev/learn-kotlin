package com.losev.myapp.domain.repository

import androidx.lifecycle.LiveData
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult

interface NoteRepository {
    fun getNotes(): LiveData<NoteResult>
    fun getNote(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}
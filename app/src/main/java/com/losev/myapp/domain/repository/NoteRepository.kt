package com.losev.myapp.domain.repository

import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.domain.model.User
import kotlinx.coroutines.channels.ReceiveChannel

interface NoteRepository {
    fun getNotes(): ReceiveChannel<NoteResult>
    suspend fun getNote(noteId: String): Note
    suspend fun saveNote(note: Note)
    suspend fun deleteNote(noteId: String)
    suspend fun getUser(): User?
}
package com.losev.myapp.domain.usecases

import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.repository.NoteRepository

class NoteInteractor(private val noteRepository: NoteRepository) {
    suspend fun getUser() = noteRepository.getUser()
    fun getNotes() = noteRepository.getNotes()
    suspend fun getNote(noteId: String) = noteRepository.getNote(noteId)
    suspend fun saveNote(note: Note) = noteRepository.saveNote(note)
    suspend fun deleteNote(noteId: String) = noteRepository.deleteNote(noteId)
}
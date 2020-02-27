package com.losev.myapp.domain.usecases

import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.repository.NoteRepository

class NoteInteractor(private val noteRepository: NoteRepository) {

    fun getNotes() = noteRepository.getNotes()
    fun getNote(id: String) = noteRepository.getNote(id)
    fun saveNote(note: Note) = noteRepository.saveNote(note)
}
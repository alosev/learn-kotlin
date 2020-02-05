package com.losev.myapp.domain.usecases

import com.losev.myapp.domain.repository.NoteRepository

class NoteIteractor(private val noteRepository: NoteRepository) {

    public fun getNotes() = noteRepository.getNotes()

}
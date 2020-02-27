package com.losev.myapp.ui.note

import androidx.lifecycle.ViewModel
import com.losev.myapp.App
import com.losev.myapp.domain.model.Note

class NoteViewModel: ViewModel() {
    private var pendingNote: Note? = null;

    fun save(note: Note){
        pendingNote = note;
    }

    override fun onCleared() {
        super.onCleared()

        pendingNote?.let {
            App.getNoteInteractor().saveNote(it)
        }
    }
}
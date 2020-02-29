package com.losev.myapp.ui.note

import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteData
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NoteViewModel(private val noteInteractor: NoteInteractor) : BaseViewModel<NoteData>() {

    private val pendingNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun load(noteId: String) {
        launch {
            try {
                val note = noteInteractor.getNote(noteId)
                setData(NoteData(note = note, isLoaded = true))
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    fun delete() {
        pendingNote?.let { note ->
            launch {
                try {
                    noteInteractor.deleteNote(note.id)
                    setData(NoteData(isDeleted = true))
                } catch (e: Throwable) {
                    setError(e)
                }
            }
        }
    }

    override fun onCleared() {
        launch {
            pendingNote?.let { note ->
                noteInteractor.saveNote(note)
            }
            super.onCleared()
        }
    }
}
package com.losev.myapp.ui.note

import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.base.BaseViewModel

class NoteViewModel(private val noteInteractor: NoteInteractor) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note

    init {
        viewStateLiveData.value = NoteViewState()
    }

    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(data = NoteViewState.Data(note))
    }

    fun load(noteId: String) {
        noteInteractor.getNote(noteId).observeForever { result ->
            result?.let {
                viewStateLiveData.value = when (it) {
                    is NoteResult.Success<*> -> NoteViewState(data = NoteViewState.Data(note = it.data as? Note, isLoaded = true))
                    is NoteResult.Error -> NoteViewState(error = it.error)
                }
            }
        }
    }

    fun delete() {
        pendingNote?.let { note ->
            noteInteractor.deleteNote(note.id).observeForever { result ->
                viewStateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> NoteViewState(data = NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        pendingNote?.let {
            noteInteractor.saveNote(it)
        }
    }
}
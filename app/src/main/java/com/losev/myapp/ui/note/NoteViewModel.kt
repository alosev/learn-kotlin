package com.losev.myapp.ui.note

import androidx.lifecycle.Observer
import com.losev.myapp.App
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.ui.base.BaseViewModel

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    private val noteInteractor = App.getNoteInteractor()
    private var pendingNote: Note? = null;

    init {
        viewStateLiveData.value = NoteViewState()
    }

    fun save(note: Note) {
        pendingNote = note;
    }

    fun load(id: String) {
        noteInteractor.getNote(id).observeForever(object : Observer<NoteResult> {
            override fun onChanged(result: NoteResult?) {
                result ?: return

                when (result) {
                    is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = result.data as? Note)
                    is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        })
    }

    override fun onCleared() {
        super.onCleared()

        pendingNote?.let {
            noteInteractor.saveNote(it)
        }
    }
}
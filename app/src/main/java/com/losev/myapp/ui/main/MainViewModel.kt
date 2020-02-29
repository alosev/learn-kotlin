package com.losev.myapp.ui.main

import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.base.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(private val noteInteractor: NoteInteractor) : BaseViewModel<List<Note>?>() {

    private val notesChannel = noteInteractor.getNotes()

    init {
        launch {
            notesChannel.consumeEach { result ->
                when (result) {
                    is NoteResult.Success<*> -> setData(result.data as? List<Note>)
                    is NoteResult.Error -> setError(result.error)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        notesChannel.cancel()
    }
}
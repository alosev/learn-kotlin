package com.losev.myapp.ui.main

import androidx.lifecycle.Observer
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.base.BaseViewModel

class MainViewModel(private val noteInteractor: NoteInteractor) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object: Observer<NoteResult>{
        override fun onChanged(result: NoteResult?) {
            result ?: return

            when(result){
                is NoteResult.Success<*> -> viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
                is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
            }
        }
    }

    init {
        viewStateLiveData.value = MainViewState()
        noteInteractor.getNotes().observeForever(notesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        noteInteractor.getNotes().removeObserver(notesObserver)
    }

}
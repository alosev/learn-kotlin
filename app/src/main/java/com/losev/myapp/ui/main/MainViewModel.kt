package com.losev.myapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.losev.myapp.App
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

    private val noteInteractor = App.getNoteInteractor()
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
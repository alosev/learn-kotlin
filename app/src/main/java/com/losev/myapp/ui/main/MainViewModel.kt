package com.losev.myapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.usecases.NoteIteractor

class MainViewModel(private val noteIteractor: NoteIteractor) : ViewModel() {
    private val notesLiveData: MutableLiveData<List<Note>>

    init {
        notesLiveData = MutableLiveData();
        notesLiveData.value = noteIteractor.getNotes()
    }

    public fun notesLiveData() : LiveData<List<Note>> {
        return notesLiveData
    }

}
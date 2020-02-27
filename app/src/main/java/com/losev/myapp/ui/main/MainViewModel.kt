package com.losev.myapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.losev.myapp.App
import com.losev.myapp.domain.model.Note

class MainViewModel : ViewModel() {

    private val notesLiveData: MutableLiveData<List<Note>> = MutableLiveData()

    init {
        App.getNoteInteractor().getNotes().observeForever { notes ->
            notesLiveData.value = notes
        }
    }

    fun notesLiveData(): LiveData<List<Note>> = notesLiveData

}
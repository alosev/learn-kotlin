package com.losev.myapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.losev.myapp.domain.usecases.NoteIteractor

class MainViewModelFactory(private val noteIteractor : NoteIteractor) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(noteIteractor::class.java).newInstance(noteIteractor)
    }
}
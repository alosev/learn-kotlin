package com.losev.myapp

import android.app.Application
import com.losev.myapp.data.repository.NoteRepositoryListImpl
import com.losev.myapp.domain.usecases.NoteInteractor

class App: Application() {

    companion object Factory{
        private val noteInteractor = NoteInteractor(NoteRepositoryListImpl())
        fun getNoteInteractor() = noteInteractor
    }

}
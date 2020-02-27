package com.losev.myapp

import android.app.Application
import com.losev.myapp.data.repository.NoteRepositoryFirebaseImpl
import com.losev.myapp.domain.usecases.NoteInteractor

class App: Application() {

    companion object Factory{
        private val noteInteractor = NoteInteractor(NoteRepositoryFirebaseImpl())
        fun getNoteInteractor() = noteInteractor
    }

}
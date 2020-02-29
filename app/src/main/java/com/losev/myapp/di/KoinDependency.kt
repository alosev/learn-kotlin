package com.losev.myapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.losev.myapp.data.repository.NoteRepositoryFirebaseImpl
import com.losev.myapp.domain.repository.NoteRepository
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.main.MainViewModel
import com.losev.myapp.ui.note.NoteViewModel
import com.losev.myapp.ui.splash.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { NoteRepositoryFirebaseImpl(get(), get()) } bind NoteRepository::class
    single { NoteInteractor(get()) }
}

var mainModule = module {
    viewModel { MainViewModel(get()) }
}

var noteModule = module {
    viewModel { NoteViewModel(get()) }
}

var splashModule = module {
    viewModel { SplashViewModel(get()) }
}

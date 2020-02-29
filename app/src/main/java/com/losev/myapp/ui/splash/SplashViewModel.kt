package com.losev.myapp.ui.splash

import com.losev.myapp.domain.model.NoAuthException
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.base.BaseViewModel

class SplashViewModel(private val noteInteractor: NoteInteractor) : BaseViewModel<Boolean?, SplashViewState>() {
    fun requestUser() {
        noteInteractor.getUser().observeForever { user ->
            viewStateLiveData.value = user?.let {
                SplashViewState(authenticated = true)
            } ?: SplashViewState(error = NoAuthException())
        }
    }
}
package com.losev.myapp.ui.splash

import com.losev.myapp.App
import com.losev.myapp.domain.model.NoAuthException
import com.losev.myapp.ui.base.BaseViewModel

class SplashViewModel : BaseViewModel<Boolean?, SplashViewState>() {
    private val noteInteractor = App.getNoteInteractor()

    fun requestUser() {
        noteInteractor.getUser().observeForever { user ->
            viewStateLiveData.value = user?.let {
                SplashViewState(authenticated = true)
            } ?: SplashViewState(error = NoAuthException())
        }
    }
}
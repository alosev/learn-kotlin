package com.losev.myapp.ui.splash

import com.losev.myapp.domain.model.NoAuthException
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(private val noteInteractor: NoteInteractor) : BaseViewModel<Boolean?>() {
    fun requestUser() {
        launch {
            try {
                noteInteractor.getUser()?.let {
                    setData(true)
                } ?: throw NoAuthException()
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }
}
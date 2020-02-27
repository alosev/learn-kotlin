package com.losev.myapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T, S: BaseViewState<T>>: ViewModel() {
    protected val viewStateLiveData = MutableLiveData<S>()
    fun getViewState(): LiveData<S> = viewStateLiveData
}
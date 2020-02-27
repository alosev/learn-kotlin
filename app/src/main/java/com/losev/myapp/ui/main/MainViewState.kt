package com.losev.myapp.ui.main

import com.losev.myapp.domain.model.Note
import com.losev.myapp.ui.base.BaseViewState

class MainViewState(notes: List<Note>? = null, error: Throwable? = null): BaseViewState<List<Note>?>(notes, error)
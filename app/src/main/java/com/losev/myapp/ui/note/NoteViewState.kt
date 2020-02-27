package com.losev.myapp.ui.note

import com.losev.myapp.domain.model.Note
import com.losev.myapp.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)
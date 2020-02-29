package com.losev.myapp.ui.note

import com.losev.myapp.domain.model.Note
import com.losev.myapp.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error){
    data class Data(val note: Note? = null, val isDeleted: Boolean = false, val isLoaded: Boolean = false)
}
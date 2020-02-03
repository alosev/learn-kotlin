package com.losev.myapp.data.repository

import android.content.Context
import androidx.core.content.ContextCompat
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.repository.NoteRepository

class NoteRepositoryListImpl(context: Context) : NoteRepository {

    private val notes: List<Note>

    init {
        notes = listOf(
                Note(
                        "Заметка 1",
                        "Текст заметки 1. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.pink)
                ),
                Note(
                        "Заметка 2",
                        "Текст заметки 2. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.purple)
                ),
                Note(
                        "Заметка 3",
                        "Текст заметки 3. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.blue)
                ),
                Note(
                        "Заметка 4",
                        "Текст заметки 4. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.green)
                ),
                Note(
                        "Заметка 5",
                        "Текст заметки 5. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.lime)
                ),
                Note(
                        "Заметка 6",
                        "Текст заметки 6. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.yellow)
                ),
                Note(
                        "Заметка 1",
                        "Текст заметки 1. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.pink)
                ),
                Note(
                        "Заметка 2",
                        "Текст заметки 2. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.purple)
                ),
                Note(
                        "Заметка 3",
                        "Текст заметки 3. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.blue)
                ),
                Note(
                        "Заметка 4",
                        "Текст заметки 4. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.green)
                ),
                Note(
                        "Заметка 5",
                        "Текст заметки 5. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.lime)
                ),
                Note(
                        "Заметка 6",
                        "Текст заметки 6. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.yellow)
                ),
                Note(
                        "Заметка 1",
                        "Текст заметки 1. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.pink)
                ),
                Note(
                        "Заметка 2",
                        "Текст заметки 2. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.purple)
                ),
                Note(
                        "Заметка 3",
                        "Текст заметки 3. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.blue)
                ),
                Note(
                        "Заметка 4",
                        "Текст заметки 4. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.green)
                ),
                Note(
                        "Заметка 5",
                        "Текст заметки 5. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.lime)
                ),
                Note(
                        "Заметка 6",
                        "Текст заметки 6. Не очень длинный, но интересный",
                        ContextCompat.getColor(context, R.color.yellow)
                )
        )
    }

    override fun getNotes(): List<Note> {
        return notes
    }
}
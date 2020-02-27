package com.losev.myapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.repository.NoteRepository
import java.util.*

class NoteRepositoryListImpl() : NoteRepository {

    private val notesLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    private val notes: MutableList<Note> = mutableListOf(
            Note(
                    UUID.randomUUID().toString(),
                    "Заметка 1",
                    "Текст заметки 1. Не очень длинный, но интересный",
                    Note.Color.PINK
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Заметка 2",
                    "Текст заметки 2. Не очень длинный, но интересный",
                    Note.Color.PURPLE
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Заметка 3",
                    "Текст заметки 3. Не очень длинный, но интересный",
                    Note.Color.BLUE
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Заметка 4",
                    "Текст заметки 4. Не очень длинный, но интересный",
                    Note.Color.GREEN
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Заметка 5",
                    "Текст заметки 5. Не очень длинный, но интересный",
                    Note.Color.LIME
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "Заметка 6",
                    "Текст заметки 6. Не очень длинный, но интересный",
                    Note.Color.YELLOW
            )
    )

    init {
        updateLiveData()
    }

    override fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    override fun saveNote(note: Note) {
        addOrUpdateNote(note)
        updateLiveData()
    }

    private fun addOrUpdateNote(note: Note) {
        for (i in notes.indices) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }

        notes.add(note)
    }

    private fun updateLiveData() {
        notesLiveData.value = notes
    }
}
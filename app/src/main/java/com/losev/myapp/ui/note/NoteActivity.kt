package com.losev.myapp.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note
import com.losev.myapp.extensions.getColor
import com.losev.myapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<NoteViewState.Data, NoteViewState>() {

    companion object {
        const val EXTRA_NOTE_ID = "EXTRA_NOTE_ID"
        const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun create(context: Context, noteId: String? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE_ID, noteId)
            context.startActivity(intent);
        }
    }

    override val viewModel: NoteViewModel by viewModel()
    override val layoutRes = R.layout.activity_note
    private var note: Note? = null
    private var color: Note.Color = Note.Color.WHITE

    private val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Новая заметка"

        val noteId = intent.getStringExtra(EXTRA_NOTE_ID);
        noteId?.let {
            viewModel.load(noteId)
        }

        color_picker.onColorClickListener = { newColor ->
            color = newColor
            saveNote()
        }

        addTextChangeListener()
    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) finish()

        note = data.note;
        renderNote(data.isLoaded)
    }

    private fun renderNote(isLoaded: Boolean) {
        note?.let { note ->
            if (isLoaded) {
                removeTextChangeListener()
                note_title.setText(note.title)
                note_text.setText(note.text)
                addTextChangeListener()
            }
            toolbar.setBackgroundColor(note.color.getColor(this))
            supportActionBar?.title = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note.lastChange)
        }
    }

    private fun saveNote() {
        note = note?.copy(
                title = note_title.text.toString(),
                text = note_text.text.toString(),
                lastChange = Date(),
                color = color
        ) ?: createNewNote()

        note?.let {
            viewModel.save(it)
        }
    }

    private fun createNewNote() = Note(UUID.randomUUID().toString(), note_title.text.toString(), note_text.text.toString(), color)

    private fun addTextChangeListener() {
        note_title.addTextChangedListener(TextChangeListener)
        note_text.addTextChangedListener(TextChangeListener)
    }

    private fun removeTextChangeListener() {
        note_title.removeTextChangedListener(TextChangeListener)
        note_text.removeTextChangedListener(TextChangeListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.note, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.menu_delete -> deleteNote().let { true }
        R.id.menu_palette -> togglePalette().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        alert {
            message = "Удалить?"
            positiveButton("Да") { viewModel.delete() }
            negativeButton("Нет") { dialog -> dialog.dismiss() }
        }.show()
    }

    private fun togglePalette() {
        if (color_picker.isOpen) {
            color_picker.close()
        } else {
            color_picker.open()
        }
    }
}

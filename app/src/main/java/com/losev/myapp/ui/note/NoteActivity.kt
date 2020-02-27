package com.losev.myapp.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.appbar.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "EXTRA_NOTE"
        const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun create(context: Context, note: Note? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent);
        }
    }

    private lateinit var viewModel: NoteViewModel;
    private var note: Note? = null;
    private val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra(EXTRA_NOTE);

        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(it.lastChange)
        } ?: "Новая заметка"

        initViewModel()
        initView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    private fun initView() {
        note?.let { note ->
            note_title.setText(note.title)
            note_text.setText(note.text)
            val color = convertColor(note.color)
            toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
        }

        note_title.addTextChangedListener(TextChangeListener)
        note_text.addTextChangedListener(TextChangeListener)
    }

    private fun saveNote() {
        Handler().post {
            note = note?.copy(
                    title = note_title.text.toString(),
                    text = note_text.text.toString(),
                    lastChange = Date()
            ) ?: createNewNote()

            note?.let {
                viewModel.save(it)
            }
        }
    }

    private fun createNewNote() = Note(UUID.randomUUID().toString(), note_title.text.toString(), note_text.text.toString())

    private fun convertColor(color: Note.Color): Int {
        return when (color) {
            Note.Color.WHITE -> R.color.white
            Note.Color.YELLOW -> R.color.yellow
            Note.Color.GREEN -> R.color.green
            Note.Color.BLUE -> R.color.blue
            Note.Color.LIME -> R.color.lime
            Note.Color.PURPLE -> R.color.purple
            Note.Color.PINK -> R.color.pink
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}

package com.losev.myapp.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note
import com.losev.myapp.ui.base.BaseActivity
import com.losev.myapp.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override val layoutRes = R.layout.activity_main

    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        initAdapter();

        fab.setOnClickListener {
            NoteActivity.create(this)
        }
    }

    private fun initAdapter() {
        adapter = NoteAdapter { note ->
            NoteActivity.create(this, note.id)
        }
        note_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        note_list.adapter = adapter;
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}

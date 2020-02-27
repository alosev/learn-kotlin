package com.losev.myapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.losev.myapp.App
import com.losev.myapp.R
import com.losev.myapp.data.repository.NoteRepositoryListImpl
import com.losev.myapp.domain.repository.NoteRepository
import com.losev.myapp.domain.usecases.NoteInteractor
import com.losev.myapp.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViewModel()
        initAdapter();

        viewModel.notesLiveData().observe(this, Observer {
            it?.let {
                adapter.notes = it;
            }
        })

        fab.setOnClickListener{
            NoteActivity.create(this)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initAdapter() {
        adapter = NoteAdapter { note ->
            NoteActivity.create(this, note)
        }
        note_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        note_list.adapter = adapter;
    }
}

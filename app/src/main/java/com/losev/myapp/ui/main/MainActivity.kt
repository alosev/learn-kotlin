package com.losev.myapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.losev.myapp.R
import com.losev.myapp.data.repository.NoteRepositoryListImpl
import com.losev.myapp.domain.repository.NoteRepository
import com.losev.myapp.domain.usecases.NoteIteractor
import kotlinx.android.synthetic.main.activity_main.*

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
    }

    private fun initViewModel() {
        val noteRepository: NoteRepository = NoteRepositoryListImpl(this)
        val noteIteractor: NoteIteractor = NoteIteractor(noteRepository)

        viewModel = ViewModelProvider(this, MainViewModelFactory(noteIteractor)).get(MainViewModel::class.java)
    }

    private fun initAdapter() {
        adapter = NoteAdapter()
        note_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        note_list.adapter = adapter;
    }
}

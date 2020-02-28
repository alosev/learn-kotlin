package com.losev.myapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note
import com.losev.myapp.ui.base.BaseActivity
import com.losev.myapp.ui.note.NoteActivity
import com.losev.myapp.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

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

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.menu_logout -> showLogoutDialog().let { true }
        else -> false
    }

    fun showLogoutDialog(){
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
                LogoutDialog.create().show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    SplashActivity.start(this)
                    finish()
                }
    }
}

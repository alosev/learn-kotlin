package com.losev.myapp.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.losev.myapp.R
import com.losev.myapp.domain.model.NoAuthException
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T> : AppCompatActivity(), CoroutineScope {

    companion object {
        private const val SIGN_CODE = 333
    }

    override val coroutineContext: CoroutineContext by lazy {
        Job() + Dispatchers.Main
    }

    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    abstract val viewModel: BaseViewModel<T>
    abstract val layoutRes: Int?

    override fun onStart() {
        super.onStart()

        dataJob = launch {
            viewModel.getViewState().consumeEach { data ->
                renderData(data)
            }
        }

        errorJob = launch {
            viewModel.getErrorChannel().consumeEach { error ->
                renderError(error)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutRes?.let {
            setContentView(it)
        }

        toolbar?.let {
            setSupportActionBar(it)
        }
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLogin()
            else -> error.message?.let { errorMessage ->
                showError(errorMessage)
            }
        }
    }

    protected fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startLogin() {
        val providers = listOf(
                AuthUI.IdpConfig
                        .GoogleBuilder()
                        .build()
        )

        intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build()

        startActivityForResult(intent, SIGN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_CODE && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        errorJob.cancel()
        dataJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}

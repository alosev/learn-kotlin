package com.losev.myapp.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.losev.myapp.R
import com.losev.myapp.domain.model.NoAuthException
import kotlinx.android.synthetic.main.appbar.*

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object {
        private const val SIGN_CODE = 333
    }

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutRes?.let {
            setContentView(it)
        }

        toolbar?.let {
            setSupportActionBar(it)
        }

        viewModel.getViewState().observe(this, object : Observer<S> {
            override fun onChanged(state: S?) {
                state ?: return

                state.error?.let { error ->
                    renderError(error)
                    return
                }

                renderData(state.data)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_CODE && resultCode != Activity.RESULT_OK) {
            finish()
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
}
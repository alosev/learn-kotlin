package com.losev.myapp.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.appbar.*

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        setSupportActionBar(toolbar)

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

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) {
        error.message?.let { errorMessage ->
            showError(errorMessage)
        }
    }

    protected fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
package com.losev.myapp.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.losev.myapp.ui.base.BaseActivity
import com.losev.myapp.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    companion object {
        fun start(context: Context) = Intent(context, SplashActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes: Int? = null

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            MainActivity.start(this)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            viewModel.requestUser()
        }, 1000)
    }
}

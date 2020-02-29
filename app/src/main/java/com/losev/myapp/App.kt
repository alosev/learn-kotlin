package com.losev.myapp

import android.app.Application
import com.losev.myapp.di.appModule
import com.losev.myapp.di.mainModule
import com.losev.myapp.di.noteModule
import com.losev.myapp.di.splashModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, mainModule, noteModule, splashModule))
    }
}
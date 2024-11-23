package com.medioka.notesapp

import android.app.Application

class NoteApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(application = this)
    }
}

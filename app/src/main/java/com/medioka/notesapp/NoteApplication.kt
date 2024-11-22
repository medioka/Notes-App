package com.medioka.notesapp

import android.app.Application

class NoteApplication : Application() {
    val appContainer = AppContainer(application = this)
}

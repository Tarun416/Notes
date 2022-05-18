package com.task.noteapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.task.noteapp.di.AppComponent
import com.task.noteapp.di.AppModule
import com.task.noteapp.di.DaggerAppComponent
import com.task.noteapp.ui.di.NoteModule

class NotesApp : Application() {

    private lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .noteModule(NoteModule)
            .build()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun getAppComponent()  = appComponent
}
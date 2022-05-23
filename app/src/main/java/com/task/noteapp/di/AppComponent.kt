package com.task.noteapp.di

import com.task.noteapp.ui.NotesCreateEditFragment
import com.task.noteapp.ui.NotesFragment
import com.task.noteapp.ui.di.NoteModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NoteModule::class])
@Singleton
interface AppComponent {
    fun inject(frag: NotesFragment)
    fun inject(frag: NotesCreateEditFragment)
}

package com.task.noteapp.di

import android.app.Application
import androidx.room.Room
import com.task.noteapp.db.NotesDao
import com.task.noteapp.db.NotesDatabase
import com.task.noteapp.repo.NotesRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideNoteRepo(dao: NotesDao) = NotesRepo(dao)

    @Provides
    @Singleton
    fun provideNotesDao(db: NotesDatabase): NotesDao = db.getNotesDao()

    @Provides
    @Singleton
    fun provideNoteDatabase(): NotesDatabase =
        Room.databaseBuilder(application, NotesDatabase::class.java, "notes.db")
            .fallbackToDestructiveMigration().build()
}

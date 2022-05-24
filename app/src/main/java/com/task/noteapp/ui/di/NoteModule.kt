package com.task.noteapp.ui.di
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.NotesViewModel
import com.task.noteapp.repo.NotesRepoImpl
import com.task.noteapp.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NoteModule {

    @Provides
    @Singleton
    fun provideNotesViewModel(notesViewModel: NotesViewModel): ViewModelProvider.Factory {
        return ViewModelFactory(notesViewModel)
    }

    @Provides
    @Singleton
    fun noteViewModel(repo: NotesRepoImpl): NotesViewModel {
        return NotesViewModel(repo)
    }
}

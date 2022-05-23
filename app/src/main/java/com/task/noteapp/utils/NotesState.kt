package com.task.noteapp.utils
import com.task.noteapp.ui.model.NotesUI

sealed class NotesState {
    object ShowLoading : NotesState()
    object HideLoading : NotesState()
    object Empty : NotesState()
    data class Success(val list: List<NotesUI>) : NotesState()
    data class Error(val exception: Throwable) : NotesState()
}

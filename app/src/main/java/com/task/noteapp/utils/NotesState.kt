package com.task.noteapp.utils

import com.task.noteapp.model.Notes

sealed class NotesState
{
    object ShowLoading : NotesState()
    object HideLoading : NotesState()
    object Empty : NotesState()
    data class Success(val list : List<Notes>) :NotesState()
    data class Error(val exception : Throwable) :NotesState()
}

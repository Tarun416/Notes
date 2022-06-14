package com.task.noteapp.repo
import com.task.noteapp.model.Notes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface NotesRepo {
    fun getNotes(): Observable<List<Notes>>
    fun insertNotes(notes: Notes) : Completable
    fun updateNotes(notes: Notes) : Completable
    fun deleteNotes(id: Int) : Completable
}

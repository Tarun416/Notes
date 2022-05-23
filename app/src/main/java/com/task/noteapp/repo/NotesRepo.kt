package com.task.noteapp.repo

import com.task.noteapp.db.NotesDao
import com.task.noteapp.model.Notes
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NotesRepo @Inject constructor(private val dao: NotesDao) {

    fun getNotes(): Observable<List<Notes>> {
        return dao.getNotes()
    }

    fun insertNotes(notes: Notes) {
        return dao.insertNotes(notes)
    }

    fun updateNotes(notes: Notes) {
        return dao.updateNotes(notes)
    }

    fun deleteNotes(id: Int) {
        return dao.deleteNote(id)
    }
}

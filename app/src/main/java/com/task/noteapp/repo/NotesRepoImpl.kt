package com.task.noteapp.repo
import com.task.noteapp.db.NotesDao
import com.task.noteapp.model.Notes
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NotesRepoImpl @Inject constructor(private val dao: NotesDao) : NotesRepo {
    override fun getNotes(): Observable<List<Notes>> {
        return dao.getNotes()
    }

    override fun insertNotes(notes: Notes) {
        return dao.insertNotes(notes)
    }

    override fun updateNotes(notes: Notes) {
        return dao.updateNotes(notes)
    }

    override fun deleteNotes(id: Int) {
        return dao.deleteNote(id)
    }
}

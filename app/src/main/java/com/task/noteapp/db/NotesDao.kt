package com.task.noteapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.task.noteapp.model.Notes
import io.reactivex.rxjava3.core.Observable


@Dao
interface NotesDao {

    // insert notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertNotes(notes: Notes)

    // update notes
    @Update(onConflict = OnConflictStrategy.REPLACE)
     fun updateNotes(notes: Notes)

    // get all notes from db
    @Query("SELECT * FROM notes order by id desc")
    fun getNotes(): Observable<List<Notes>>

    // delete notes by id
    @Query("DELETE FROM notes where id=:id")
    fun deleteNote(id: Int)
}

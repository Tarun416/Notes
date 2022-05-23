package com.task.noteapp.db
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.task.noteapp.model.Notes
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesDaoTest : NotesDatabaseTest() {
    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun writeAndReadNote() {
        val note = Notes(1, "Hello", "World")
        dao.insertNotes(note)
        Truth.assertThat(dao.getNotes().blockingFirst().isEmpty()).isFalse()
    }

    @Test
    fun writeAndReadNote_addTwoData_size2returned() {
        val note = Notes(1, "Hello", "World")
        dao.insertNotes(note)
        val note1 = Notes(2, "Hello", "World")
        dao.insertNotes(note1)
        Truth.assertThat(dao.getNotes().blockingFirst().size == 2).isTrue()
    }

    @Test
    fun testdeleteNoteTest() {
        val note = Notes(1, "Hello", "World")
        dao.insertNotes(note)
        Truth.assertThat(dao.getNotes().blockingFirst().size == 1).isTrue()
        dao.deleteNote(1)
        Truth.assertThat(dao.getNotes().blockingFirst().isEmpty()).isTrue()
    }

    @Test
    fun updateNoteTest() {
        val note = Notes(1, "Hello", "World")
        dao.insertNotes(note)
        note.title = "hell"
        dao.updateNotes(note)
        assertEquals(dao.getNotes().blockingFirst()[0].title, "hell")
    }
}

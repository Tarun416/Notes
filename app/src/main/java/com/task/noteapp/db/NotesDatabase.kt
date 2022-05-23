package com.task.noteapp.db
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.noteapp.model.Notes
import com.task.noteapp.utils.Converters

@Database(
    entities = [Notes::class],
    version = 8,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}

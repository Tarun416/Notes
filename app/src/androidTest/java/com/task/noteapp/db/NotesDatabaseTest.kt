package com.task.noteapp.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
abstract class NotesDatabaseTest : TestCase()
{

   // @JvmField @Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
   // @JvmField @Rule val mainActivity = ActivityTestRule<MainActivity>(MainActivity::class.java)
    // get reference to the LanguageDatabase and LanguageDao class
    private lateinit var db: NotesDatabase
    protected lateinit var dao: NotesDao

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {

        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context,NotesDatabase::class.java).build()
        dao = db.getNotesDao()
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }






}
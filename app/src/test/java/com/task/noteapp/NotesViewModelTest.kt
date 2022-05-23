package com.task.noteapp

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.task.noteapp.repo.NotesRepo
import com.task.noteapp.utils.NotesState
import org.junit.Assert.*
import org.junit.Test
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith
import org.junit.Assert.assertThat
import org.mockito.Mockito.*
import com.task.noteapp.model.Notes
import com.task.noteapp.ui.model.NotesUI
import com.task.noteapp.utils.BaseJUnitTest
import io.reactivex.rxjava3.core.Observable
import org.hamcrest.CoreMatchers.*
import org.mockito.Mockito.verify
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class NotesViewModelTest : BaseJUnitTest() {

    private lateinit var vm: NotesViewModel
    private val repo = mock<NotesRepo>()
    private var observer = mock<Observer<NotesState>>()

    override fun start() {
        vm = NotesViewModel(
            repo
        )
    }

    override fun stop() {
        verifyNoMoreInteractions(repo)
    }

    @Test
    fun `getNotes should show empty list when data source is empty`() {
        val notes = mutableListOf<Notes>()
        given(repo.getNotes()).willReturn { Observable.fromCallable { notes } }
        // then
        vm.livedata.observeForever(observer)
        // that
        vm.getAllNotes()
        verify(observer).onChanged(NotesState.ShowLoading)
        verify(observer).onChanged(NotesState.HideLoading)
        verify(observer).onChanged(NotesState.Empty)
        verify(repo).getNotes()
    }

    @Test
    fun `getNotes should show items in list when data source has atleast one entry`() {

        val single = Observable.just(
            listOf(
                Notes(1, "D", "1")
            )
        )
        val singleUI = Observable.just(
            listOf(
                NotesUI(1, "D", "1")
            )
        )

        given(repo.getNotes()).willReturn { single }

        // then
        vm.livedata.observeForever(observer)
        // that
        vm.getAllNotes()

        verify(observer).onChanged(NotesState.ShowLoading)
        verify(observer).onChanged(NotesState.HideLoading)
        verify(observer).onChanged(NotesState.Success(singleUI.blockingFirst()))
        verify(repo).getNotes()
    }

    @Test
    fun `getNotes should emit error when data source throws an throwable`() {
        val throwable = Throwable("mock")
        given(repo.getNotes()).willReturn(Observable.error(throwable))
        // then
        vm.livedata.observeForever(observer)
        // that
        vm.getAllNotes()
        verify(observer).onChanged(NotesState.ShowLoading)
        verify(observer).onChanged(NotesState.HideLoading)
        verify(observer).onChanged(NotesState.Error(throwable))
        verify(repo).getNotes()
    }

    @Test
    fun `fetchNotes should return note correctly`() {
        val note1 = Notes(1, "mercedes", "e")
        val note2 = Notes(2, "bmw", "e")

        val noteUI1 = NotesUI(1, "mercedes", "e")
        val noteUI2 = NotesUI(2, "bmw", "e")

        val notelist = listOf(note1, note2)
        val notelistUI = listOf(noteUI1, noteUI2)

        given(repo.getNotes()).willReturn(
            Observable.just(
                notelist
            )
        )

        vm.livedata.observeForever(observer)
        vm.getAllNotes()
        verify(observer).onChanged(NotesState.ShowLoading)
        verify(observer).onChanged(NotesState.Success(notelistUI))
        verify(repo).getNotes()
    }

    @Test
    fun checkLiveData_whenFetchAllNotes_shouldReturnSame() {
        val note1 = Notes(1, "mercedes", "e")
        val note2 = Notes(2, "bmw", "e")

        val notelist = listOf(note1, note2)

        `when`(repo.getNotes()).thenReturn(
            Observable.just(
                notelist
            )
        )

        vm.getAllNotes()
        vm.livedata.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is NotesState.Success) {
                assertTrue(it.list[0].title == "mercedes")
            }
        }
        verify(repo).getNotes()
    }
}

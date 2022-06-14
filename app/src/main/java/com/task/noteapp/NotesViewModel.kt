package com.task.noteapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.noteapp.model.Notes
import com.task.noteapp.repo.NotesRepo
import com.task.noteapp.ui.model.NotesUI
import com.task.noteapp.utils.NotesState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotesViewModel
@Inject constructor(private val repo: NotesRepo) : ViewModel() {

    private val noteLiveData = MutableLiveData<NotesState>()
    private var compositeDisposable = CompositeDisposable()
    private val sdf = SimpleDateFormat("dd-MMM-yyyy hh:mm aa", Locale.getDefault())

    val livedata: LiveData<NotesState>
        get() = noteLiveData

    fun insert(taskName: String, taskDesc: String) {
        noteLiveData.value = NotesState.ShowLoading
        val note = Notes(title = taskName, description = taskDesc, createdAt = Date())

        repo.insertNotes(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }

            })

        /* Single.fromCallable { repo.insertNotes(note) }
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(Consumer<Any?> { })*/
    }

    fun update(id: Int, taskName: String, taskDesc: String, createdAt: String?) {
        noteLiveData.value = NotesState.ShowLoading
        val note = Notes(
            id,
            title = taskName,
            description = taskDesc,
            edited = true,
            createdAt = sdf.parse(createdAt!!)
        )
        repo.updateNotes(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }

            })

        /*Single.fromCallable { repo.updateNotes(note) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<Any?> { })*/
    }

    fun deleteById(id: Int) {
        noteLiveData.value = NotesState.ShowLoading

        repo.deleteNotes(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }

            })

        /*Single.fromCallable { repo.deleteNotes(id) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<Any?> { })*/
    }

    fun getAllNotes() {
        noteLiveData.value = NotesState.ShowLoading
        repo.getNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.rxjava3.core.Observer<List<Notes>> {

                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d!!)
                }

                override fun onNext(t: List<Notes>?) {

                    noteLiveData.value = NotesState.HideLoading
                    val noteList = mutableListOf<NotesUI>()
                    for (note in t!!) {
                        val date =
                            if (note.createdAt != null) sdf.format(note.createdAt ?: "") else null
                        noteList.add(
                            NotesUI(
                                note.id,
                                note.title,
                                note.description,
                                note.edited,
                                date
                            )
                        )
                    }
                    if (noteList.isEmpty())
                        noteLiveData.postValue(NotesState.Empty)
                    else
                        noteLiveData.postValue(NotesState.Success(noteList))
                }

                override fun onError(e: Throwable?) {
                    noteLiveData.value = NotesState.HideLoading
                    noteLiveData.postValue(NotesState.Error(e!!))
                }

                override fun onComplete() {
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

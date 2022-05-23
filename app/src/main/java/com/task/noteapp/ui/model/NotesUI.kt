package com.task.noteapp.ui.model
import java.io.Serializable

data class NotesUI(
    var id: Int,
    var title: String,
    var description: String,
    var edited: Boolean? = false,
    var createdAt: String? = null
) : Serializable

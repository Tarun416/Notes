package com.task.noteapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.ItemNotesBinding
import com.task.noteapp.ui.model.NotesUI

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesVH>() {

    private val differCallback = object : DiffUtil.ItemCallback<NotesUI>() {
        override fun areItemsTheSame(oldItem: NotesUI, newItem: NotesUI): Boolean {
            return oldItem.createdAt == newItem.createdAt
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NotesUI, newItem: NotesUI): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {

        val binding =
            ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesVH(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {

        val item = differ.currentList[position]
        holder.binding.apply {

            itemNotesTitle.text = item.title
            itemNotesDesc.text = item.description

            if (item.edited!!)
                editedTag.visibility = View.VISIBLE
            else
                editedTag.visibility = View.GONE

            createdAt.text = item.createdAt

            // on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    inner class NotesVH(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    // on item click listener
    private var onItemClickListener: ((NotesUI) -> Unit)? = null
    fun setOnItemClickListener(listener: (NotesUI) -> Unit) {
        onItemClickListener = listener
    }
}

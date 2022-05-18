package com.task.noteapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.ItemNotesBinding
import com.task.noteapp.model.Notes
import java.text.SimpleDateFormat

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesVH>() {

    private val sdf = SimpleDateFormat("dd-MMM-yyyy hh:mm aa")

    private val differCallback = object : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
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

           if(item.edited!!)
               editedTag.visibility = View.VISIBLE
            else
               editedTag.visibility = View.GONE

            createdAt.text = sdf.format(item.createdAt)

            // on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    inner class NotesVH(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    // on item click listener
    private var onItemClickListener: ((Notes) -> Unit)? = null
    fun setOnItemClickListener(listener: (Notes) -> Unit) {
        onItemClickListener = listener
    }
}

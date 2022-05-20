package com.task.noteapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.noteapp.NotesApp
import com.task.noteapp.NotesViewModel
import com.task.noteapp.R
import com.task.noteapp.adapter.NotesAdapter
import com.task.noteapp.databinding.NoteFragmentBinding
import com.task.noteapp.model.Notes
import com.task.noteapp.ui.model.NotesUI
import com.task.noteapp.utils.NotesState
import java.text.SimpleDateFormat
import javax.inject.Inject

class NotesFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: NoteFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NotesViewModel

    private val noteAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(NotesViewModel::class.java)
        viewModel.getAllNotes()
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as NotesApp).getAppComponent().inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        setRv()
        observeState()
    }

    private fun observeState() {
        viewModel.livedata.observe(requireActivity()) {
            when (it) {
                NotesState.Empty -> {
                    loadEmptyState()
                }
                is NotesState.Error -> {
                    showErrorMessage(it.exception.message!!)

                }
                NotesState.ShowLoading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                NotesState.HideLoading -> {
                    binding.progress.visibility = View.GONE
                }
                is NotesState.Success -> {
                    loadSuccessState(it.list)
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun loadSuccessState(list: List<NotesUI>) {
        noteAdapter.differ.submitList(list)
    }

    private fun loadEmptyState() {
        binding.emptyStateLayout.visibility = View.VISIBLE
        noteAdapter.differ.submitList(emptyList())
    }


    private fun setRv() {
        binding.notesRv.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun setClickListener() {
        binding.btnAddNotes.setOnClickListener(this)
        noteAdapter.setOnItemClickListener {

            findNavController().navigate(
                NotesFragmentDirections.actionNavHomeToTaskEditFragment(
                    NotesUI(it.id,title =  it.title, description = it.description, createdAt = it.createdAt)
                )
            )
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_add_notes -> {
                val action =
                    NotesFragmentDirections.actionNavHomeToTaskEditFragment(null)
                findNavController().navigate(action)
            }
        }
    }
}
package com.task.noteapp.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.noteapp.NotesApp
import com.task.noteapp.NotesViewModel
import com.task.noteapp.R
import com.task.noteapp.databinding.NoteEditFragmentBinding
import com.task.noteapp.utils.hideKeyboard

import javax.inject.Inject

class NotesCreateEditFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: NoteEditFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NotesViewModel
    private val args: NotesCreateEditFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(NotesViewModel::class.java)

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
        binding = NoteEditFragmentBinding.inflate(inflater, container, false)
        actionBarSetUp()
        return binding.root
    }

    private fun actionBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        if (args.notes != null)
            getString(R.string.update_note).also { binding.toolbar.title = it }
        else
            getString(R.string.add_note).also { binding.toolbar.title = it }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickLisntener()

        // receiving bundles here
        val notes = args.notes ?: null
        val id = notes?.id ?: 0

        with(binding) {
            noteLayout.titleET.setText(notes?.title)
            noteLayout.noteET.setText(notes?.description)
        }
    }

    private fun getNoteContent() = binding.noteLayout.let {
        Pair(
            it.titleET.text.toString(),
            it.noteET.text.toString()
        )
    }


    private fun setClickLisntener() {
        binding.saveNotes.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

            R.id.saveNotes -> {
                val (title, note) = getNoteContent()

                when {
                    title.trim().isEmpty() -> {
                        Toast.makeText(
                            activity, getString(R.string.empty_title_msg), Toast.LENGTH_SHORT
                        ).show()
                    }
                    note.trim().isEmpty() -> {
                        Toast.makeText(
                            activity, getString(R.string.empty_desc_msg), Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {

                        requireActivity().hideKeyboard(requireView())
                        if (args.notes != null)
                            viewModel.update(
                                args.notes!!.id,
                                title.trim(),
                                note.trim(),
                                args.notes!!.createdAt
                            ).also {
                                findNavController().navigateUp()
                                Toast.makeText(
                                    activity,
                                    getString(R.string.note_updated_msg),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        else
                            viewModel.insert(title.trim(), note.trim()).also {
                                findNavController().navigateUp()
                                Toast.makeText(
                                    activity,
                                    getString(R.string.note_saved_msg),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (args.notes != null)
            inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {

            R.id.action_delete -> {
                requireActivity().hideKeyboard(requireView())
                viewModel.deleteById(args.notes!!.id).also {
                    findNavController().navigateUp()
                    Toast.makeText(
                        activity,
                        getString(R.string.note_deleted_msg),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
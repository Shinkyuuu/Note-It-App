package com.example.notes.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.TextUtils.indexOf
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.NoteLayoutBinding
import com.example.notes.objects.Note
import com.example.notes.viewmodels.NoteVM
import com.example.notes.views.fragments.MainScreen
import com.example.notes.views.fragments.MainScreenDirections

class RecyclerviewAdapter(val context: MainScreen) : RecyclerView.Adapter<RecyclerviewAdapter.RecyclerviewHolder>() {
    private var noteList = emptyList<Note>()
    private var selectNoteList = emptyList<Note>()
    private var selectCounter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerviewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerviewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.titleView.text = currentItem.title
        holder.dateView.text = currentItem.date
        holder.colorView.background = context.resources.getDrawable(R.drawable.note_enter_info_bg)

        //If user clicks on the note, open the note update screen
        holder.bodyView.setOnClickListener {
            if (selectCounter > 0) {
                toggleSelectModeVisual(holder, position)
            } else {
                val action = MainScreenDirections.actionMainScreenToUpdateNoteScreen(currentItem)
                holder.itemView.findNavController().navigate(action)
            }
        }


        holder.bodyView.setOnLongClickListener {
            toggleSelectModeVisual(holder, position)
            return@setOnLongClickListener true
        }
    }

    //Change the background of the selected notes
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun toggleSelectModeVisual(holder: RecyclerviewHolder, position: Int) {
        val selectCurrentItem = selectNoteList[position]
        selectCurrentItem.selected = !selectCurrentItem.selected

        if (selectCurrentItem.selected) {
            if (selectCounter == 0) context.turnOnSelectMode()

            selectCounter++
            holder.colorView.background = context.resources.getDrawable(R.drawable.note_selected_bg)
        } else {
            if (selectCounter == 1) context.turnOffSelectMode()

            selectCounter--
            holder.colorView.background = context.resources.getDrawable(R.drawable.note_enter_info_bg)
        }
    }

    //retrieve the notes from the database (upon screen initialization and note update)
    fun setNoteData(noteList: List<Note>) {
        this.noteList = noteList
        if (selectNoteList.isEmpty()) selectNoteList = noteList

        notifyDataSetChanged()
    }

    //Send notes that are marked for deletion to mainScreen
    fun receiveToDeleteNotes() : List<Note> {
        val toDeleteNotes : MutableList<Note> = ArrayList()

        for (note in selectNoteList) {
            if (note.selected) {
                toDeleteNotes.add(noteList[selectNoteList.indexOf(note)])
            }
        }

        return toDeleteNotes
    }

    //Unselect all selected notes
    fun unselectAll() {
        for (note in selectNoteList) {
            note.selected = false
        }
        selectCounter = 0
        notifyDataSetChanged()
    }

    override fun getItemCount() = noteList.size

    class RecyclerviewHolder(binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.noteTitle
        val dateView: TextView = binding.noteDate
        val colorView: RelativeLayout = binding.noteColor
        val bodyView: CardView = binding.note
    }
}

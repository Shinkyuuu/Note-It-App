package com.example.notes.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerviewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerviewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.titleView.text = currentItem.title
        holder.dateView.text = currentItem.date
        holder.colorView.background = generateColor(currentItem.color)

        //If user clicks on the note, open the note update screen
        holder.bodyView.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToUpdateNoteScreen(currentItem)

            holder.itemView.findNavController().navigate(action)
        }

        holder.bodyView.setOnLongClickListener {
            var selectCurrentItem = selectNoteList[position]
            selectCurrentItem.selected = !selectCurrentItem.selected

            holder.colorView.background =
                if (selectCurrentItem.selected) generateColor("Green") else generateColor(
                    selectCurrentItem.color
                )
            println("Done $position ${currentItem.id}")
            return@setOnLongClickListener true
        }
    }


    //Change the color of the note's background
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun generateColor(color: String): Drawable {
        return when (color) {
            "orange" -> context.resources.getDrawable(R.drawable.note_bg_orange)
            "blue" -> context.resources.getDrawable(R.drawable.note_bg_blue)
            "purple" -> context.resources.getDrawable(R.drawable.note_bg_purple)
            "pink" -> context.resources.getDrawable(R.drawable.note_bg_pink)
            "green" -> context.resources.getDrawable(R.drawable.note_bg_green)
            else -> context.resources.getDrawable(R.drawable.note_bg_orange)
        }
    }

    //retrieve the notes from the database (upon screen initialization and note update)
    fun setNoteData(noteList: List<Note>) {
        this.noteList = noteList

        if (selectNoteList.isEmpty()) selectNoteList = noteList

        notifyDataSetChanged()
    }

    //Delete the  selected notes
    fun deleteNotes() {
        TODO("ADD DELETE FUNCTION FROM VIEWMODEL")
    }


    //Unselect all selected notes
    fun unselectAll() {
        for (note in selectNoteList) {
            note.selected = false
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = noteList.size

    class RecyclerviewHolder(binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.noteTitle
        val dateView: TextView = binding.noteDate
        val colorView: RelativeLayout = binding.noteColor
        val bodyView: CardView = binding.note
    }

    interface AdapterInterface {
        fun turnOnSelectMode()
    }

}

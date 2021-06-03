package com.example.notes.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.NoteLayoutBinding
import com.example.notes.objects.Note
import com.example.notes.views.fragments.MainScreen

class RecyclerviewAdapter(val context: MainScreen) : RecyclerView.Adapter<RecyclerviewAdapter.RecyclerviewHolder>() {
    private var noteList = emptyList<Note>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerviewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerviewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.titleView.text = currentItem.title
        holder.dateView.text = currentItem.date
        holder.colorView.background = generateColor(currentItem.color)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun generateColor(color: String) : Drawable {
        return when (color) {
            "orange" -> context.resources.getDrawable(R.drawable.note_bg_orange)
            "blue" -> context.resources.getDrawable(R.drawable.note_bg_blue)
            "purple" -> context.resources.getDrawable(R.drawable.note_bg_purple)
            "pink" -> context.resources.getDrawable(R.drawable.note_bg_pink)
            "green" -> context.resources.getDrawable(R.drawable.note_bg_green)
            else -> context.resources.getDrawable(R.drawable.note_bg_orange)
        }
    }

    fun setNoteData(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    override fun getItemCount() = noteList.size

    class RecyclerviewHolder(binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.noteTitle
        val dateView: TextView = binding.noteDate
        val colorView: RelativeLayout = binding.noteColor
    }
}

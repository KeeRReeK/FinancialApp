package com.example.financialapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialapp.DB.Note

class NotesAdapter(private var notes: List<Note>, context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val category: TextView = itemView.findViewById(R.id.categoryTextView)
        val count: TextView = itemView.findViewById(R.id.countTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var viewType: Int = 0

    companion object {
        const val VIEW_TYPE_INCOME = 1
        const val VIEW_TYPE_EXPENSE = 2
    }

    // Визначте, який макет використовувати в залежності від типу запису
    override fun getItemViewType(position: Int): Int {
        return if (notes[position].type == "Дохід") {
            VIEW_TYPE_INCOME
        } else {
            VIEW_TYPE_EXPENSE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_INCOME) {
            R.layout.note_item_income
        } else {
            R.layout.note_item_expense
        }
        val view = inflater.inflate(layoutId, parent, false)
        return NoteViewHolder(view)
    }


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
//        return NoteViewHolder(view)
//    }

    override fun getItemCount(): Int = notes.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.category.text = note.category
        holder.count.text = "${note.count}₴"
        holder.description.text = note.description
    }

    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}
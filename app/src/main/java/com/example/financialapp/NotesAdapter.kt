package com.example.financialapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.financialapp.DB.Note
import com.example.financialapp.DB.NotesDataBaseHelper
import com.example.financialapp.DB.UpdateNoteActivity

class NotesAdapter(private var notes: List<Note>, context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val db: NotesDataBaseHelper = NotesDataBaseHelper(context)
    private lateinit var notesRecyclerView: RecyclerView


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val category: TextView = itemView.findViewById(R.id.categoryTextView)
        val count: TextView = itemView.findViewById(R.id.countTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        val date: TextView = itemView.findViewById(R.id.dateTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
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

    override fun getItemCount(): Int = notes.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.category.text = note.category
        holder.count.text = "${note.count}₴"
        holder.description.text = note.description
        holder.date.text = note.date
        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            if(note.type == "Дохід"){
                refreshData(db.getAllIncomeNotes())
            } else {
                refreshData(db.getAllExpenseNotes())
            }
            Toast.makeText(holder.itemView.context, "Запис видалений", Toast.LENGTH_SHORT).show()
        }
    }

//        holder.deleteButton.setOnClickListener{
//            db.deleteNote(note.id)
//            if(note.type == "Дохід"){
//                refreshData(db.getAllIncomeNotes())
//            } else {
//                refreshData(db.getAllExpenseNotes())
//            }
//            Toast.makeText(holder.itemView.context, "Запис видалений", Toast.LENGTH_SHORT).show()
//        }
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}
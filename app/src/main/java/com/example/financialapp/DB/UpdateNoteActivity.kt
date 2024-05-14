package com.example.financialapp.DB

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financialapp.databinding.ActivityUpdateNoteBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDataBaseHelper
    private var noteId: Int = -1
    private lateinit var originalNote: Note

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDataBaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        originalNote = db.getNoteById(noteId)

        setDecimalDigitsInputFilter(binding.editCount, 2)


        binding.editDescriptionEntry.setText(originalNote.description)

        binding.updateSaveButton.setOnClickListener {
            val currentDate = Date()
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateText: String = dateFormat.format(currentDate)
            val newCount: Double = binding.editCount.text.toString().toDoubleOrNull() ?: 0.0
            val newDescription = binding.editDescriptionEntry.text.toString()
            val updateNote = Note(
                noteId,
                newCount,
                originalNote.type,
                originalNote.category,
                newDescription,
                dateText
            )
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this@UpdateNoteActivity, "Зміни збережені", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setDecimalDigitsInputFilter(editText: EditText, decimalDigits: Int) {
        editText.filters = arrayOf(object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                val newLength = dest?.length ?: (0 - (dend - dstart) + (end - start))
                val dotPosition = dest?.indexOf(".") ?: -1

                if (dotPosition > -1 && newLength - dotPosition > decimalDigits) {
                    return ""
                }
                return null
            }
        })
    }
}


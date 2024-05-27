package com.example.financialapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.DialogInterface
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.financialapp.DB.NotesDataBaseHelper
import com.example.financialapp.R

class SettingsFragment : Fragment() {

    private lateinit var clearAllNotes: Button
    private lateinit var builder: AlertDialog.Builder
    private lateinit var db: NotesDataBaseHelper

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Створюємо представлення для фрагмента
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Знаходимо елементи після створення представлення
        clearAllNotes = view.findViewById(R.id.clearAllNotes)
        db = NotesDataBaseHelper(requireContext())
        builder = AlertDialog.Builder(requireContext())

        clearAllNotes.setOnClickListener {
            builder.setTitle("Попередження")
                .setMessage("Ви дійсно хочете знищити усі записи?")
                .setCancelable(true)
                .setPositiveButton("Так", DialogInterface.OnClickListener { dialog, id ->
                    db.clearAllData()
                })
                .setNegativeButton("Ні", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
                .create()
                .show()
        }

        return view
    }
}

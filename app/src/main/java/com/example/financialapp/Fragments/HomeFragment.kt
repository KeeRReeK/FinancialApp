package com.example.financialapp.Fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financialapp.DB.Note
import com.example.financialapp.DB.NotesDataBaseHelper
import com.example.financialapp.NotesAdapter
import com.example.financialapp.R
import com.example.financialapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var db: NotesDataBaseHelper
    private lateinit var notesAdapter: NotesAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = NotesDataBaseHelper(requireContext())
        notesAdapter = NotesAdapter(db.getAllIncomeNotes(), requireContext())

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = notesAdapter

        binding.buttonProfit.setOnClickListener {
            notesAdapter.refreshData(db.getAllIncomeNotes())
        }

        binding.buttonExpensive.setOnClickListener {
            notesAdapter.refreshData(db.getAllExpenseNotes())
        }

        val totalCountTextView: TextView = binding.root.findViewById(R.id.totalCount)
        val colorGreen = ContextCompat.getColor(requireContext(), R.color.green)
        val colorRed = ContextCompat.getColor(requireContext(), R.color.red)
        if(db.getTotalCount() < 0){
            totalCountTextView.text = "${db.getTotalCount()}₴"
            totalCountTextView.setTextColor(colorRed)
        } else {
            totalCountTextView.text = "${db.getTotalCount()}₴"
            totalCountTextView.setTextColor(colorGreen)
        }

        binding.refreshImage.setOnClickListener {
        val colorGreen = ContextCompat.getColor(requireContext(), R.color.green)
        val colorRed = ContextCompat.getColor(requireContext(), R.color.red)
        notesAdapter.refreshData(db.getAllIncomeNotes())
        val totalCountTextView: TextView = binding.root.findViewById(R.id.totalCount)
        if(db.getTotalCount() < 0){
            totalCountTextView.text = "${db.getTotalCount()}₴"
            totalCountTextView.setTextColor(colorRed)
        } else {
            totalCountTextView.text = "${db.getTotalCount()}₴"
            totalCountTextView.setTextColor(colorGreen)
        }
        }

    }

//    fun updateDataAfterDialogDismissal() {
//        notesAdapter.refreshData(db.getAllExpenseNotes())
//        notesAdapter.refreshData(db.getAllIncomeNotes())
//        val totalCountTextView: TextView = binding.root.findViewById(R.id.totalCount)
//        if(db.getTotalCount() < 0){
//            totalCountTextView.text = "${db.getTotalCount()}₴"
//            totalCountTextView.setTextColor(Color.RED)
//        } else {
//            totalCountTextView.text = "${db.getTotalCount()}₴"
//            totalCountTextView.setTextColor(Color.GREEN)
//        }
//    }

}
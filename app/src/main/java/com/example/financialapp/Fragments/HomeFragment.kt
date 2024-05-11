package com.example.financialapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financialapp.DB.Note
import com.example.financialapp.DB.NotesDataBaseHelper
import com.example.financialapp.NotesAdapter
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
    }

    override fun onPause() {
        super.onPause()
        notesAdapter.refreshData(db.getAllIncomeNotes())
    }

}
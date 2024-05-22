package com.example.financialapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financialapp.DB.NotesDataBaseHelper
import com.example.financialapp.NotesAdapter

import com.example.financialapp.R
import com.example.financialapp.databinding.FragmentHomeBinding
import com.example.financialapp.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {


    private lateinit var db: NotesDataBaseHelper
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = NotesDataBaseHelper(requireContext())

        // Оновлення TextView для доходів
        val incomeDepositTextView = view.findViewById<TextView>(R.id.incomeDeposit)
        val incomeSalaryTextView = view.findViewById<TextView>(R.id.incomeSalary)
        val incomeSavingsTextView = view.findViewById<TextView>(R.id.incomeSavings)

        incomeDepositTextView.text = db.getTotalCategory("Депозит").toString()
        incomeSalaryTextView.text = db.getTotalCategory("Зарплата").toString()
        incomeSavingsTextView.text = db.getTotalCategory("Збереження").toString()

        // Оновлення TextView для витрат
        val expensiveCarTextView = view.findViewById<TextView>(R.id.expensiveCar)
        val expensiveClothingTextView = view.findViewById<TextView>(R.id.expensiveClothing)
        val expensiveCommunicationTextView = view.findViewById<TextView>(R.id.expensiveCommunication)
        val expensiveEatingOutTextView = view.findViewById<TextView>(R.id.expensiveEatingOut)
        val expensiveEntertainmentTextView = view.findViewById<TextView>(R.id.expensiveEntertainment)
        val expensiveFoodTextView = view.findViewById<TextView>(R.id.expensiveFood)
        val expensiveGiftsTextView = view.findViewById<TextView>(R.id.expensiveGifts)
        val expensiveHealthTextView = view.findViewById<TextView>(R.id.expensiveHealth)
        val expensiveHomeTextView = view.findViewById<TextView>(R.id.expensiveHome)
        val expensivePetsTextView = view.findViewById<TextView>(R.id.expensivePets)
        val expensiveSportTextView = view.findViewById<TextView>(R.id.expensiveSport)
        val expensiveTaxiTextView = view.findViewById<TextView>(R.id.expensiveTaxi)
        val expensiveHygieneProductsTextView = view.findViewById<TextView>(R.id.expensiveHygieneProducts)
        val expensivePublicTransportationTextView = view.findViewById<TextView>(R.id.expensivePublicTransportation)
        val expensiveAccountsTextView = view.findViewById<TextView>(R.id.expensiveAccounts)

        expensiveCarTextView.text = db.getTotalCategory("Автомобіль").toString()
        expensiveClothingTextView.text = db.getTotalCategory("Одяг").toString()
        expensiveCommunicationTextView.text = db.getTotalCategory("Комунікація").toString()
        expensiveEatingOutTextView.text = db.getTotalCategory("Харчування поза домом").toString()
        expensiveEntertainmentTextView.text = db.getTotalCategory("Розваги").toString()
        expensiveFoodTextView.text = db.getTotalCategory("Їжа").toString()
        expensiveGiftsTextView.text = db.getTotalCategory("Подарунки").toString()
        expensiveHealthTextView.text = db.getTotalCategory("Здоров'я").toString()
        expensiveHomeTextView.text = db.getTotalCategory("Будинок").toString()
        expensivePetsTextView.text = db.getTotalCategory("Домашні тварини").toString()
        expensiveSportTextView.text = db.getTotalCategory("Спорт").toString()
        expensiveTaxiTextView.text = db.getTotalCategory("Таксі").toString()
        expensiveHygieneProductsTextView.text = db.getTotalCategory("Засоби гігієни").toString()
        expensivePublicTransportationTextView.text = db.getTotalCategory("Транспорт").toString()
        expensiveAccountsTextView.text = db.getTotalCategory("Рахунки").toString()

    }


}
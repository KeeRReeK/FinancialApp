import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.financialapp.DB.Note
import com.example.financialapp.DB.NotesDataBaseHelper
import com.example.financialapp.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MyBottomDialog : DialogFragment() {

    private lateinit var db: NotesDataBaseHelper
    private lateinit var sumEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var selectingTypeSpinner: Spinner
    private lateinit var selectingCategorySpinner: Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.activity_bottom_dialog, null)

        db = NotesDataBaseHelper(requireContext())

        builder.setView(view)

        // Отримуємо посилання на елементи з макету
        sumEditText = view.findViewById(R.id.sum)
        descriptionEditText = view.findViewById(R.id.DescriptionEntry)
        selectingTypeSpinner = view.findViewById(R.id.SelectingType)
        selectingCategorySpinner = view.findViewById(R.id.SelectingCategory)

        // Створюємо адаптер для першого Spinner
        val adapterType = ArrayAdapter.createFromResource(requireContext(), R.array.income_expense_array, android.R.layout.simple_spinner_item)
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectingTypeSpinner.adapter = adapterType

        // Обробник вибору елемента у першому Spinner
        selectingTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Отримуємо вибраний елемент
                val selectedItem = parent.getItemAtPosition(position).toString()

                // В залежності від вибраного елемента в першому Spinner,
                // встановлюємо відповідний адаптер для другого Spinner
                when (selectedItem) {
                    "Дохід" -> {
                        val adapterIncome = ArrayAdapter.createFromResource(requireContext(), R.array.income_array, android.R.layout.simple_spinner_item)
                        adapterIncome.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        selectingCategorySpinner.adapter = adapterIncome
                    }
                    "Витрати" -> {
                        val adapterExpense = ArrayAdapter.createFromResource(requireContext(), R.array.expense_array, android.R.layout.simple_spinner_item)
                        adapterExpense.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        selectingCategorySpinner.adapter = adapterExpense
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Заглушка
            }
        }

        // Додаємо кнопки позитивного та негативного відповіді
        builder.setPositiveButton("Підтвердити") { dialog, which ->
            val sumText = sumEditText.text.toString()
            val count = sumText.toDoubleOrNull()

            if (count == null) {
                // Якщо введений текст не може бути сконвертований в Double, показати повідомлення про помилку
                Toast.makeText(requireContext(), "Будь ласка, введіть дійсне число", Toast.LENGTH_SHORT).show()
            } else {
                val description = descriptionEditText.text.toString()
                val type = selectingTypeSpinner.selectedItem.toString()
                val category = selectingCategorySpinner.selectedItem.toString()
                val currentDate = Date()
                val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val dateText: String = dateFormat.format(currentDate)
                val note = Note(0, count, type, category, description, dateText)

                db.insertNote(note)
                Toast.makeText(requireContext(), "Додано", Toast.LENGTH_SHORT).show()
            }

            // Тут ви можете використовувати значення змінних для подальшої обробки
        }

        builder.setNegativeButton("Скасувати") { dialog, which ->
            dialog.dismiss()
        }

        return builder.create()
    }
}

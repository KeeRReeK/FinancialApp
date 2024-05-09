import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.financialapp.R

class MyBottomDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.activity_bottom_dialog, null)

        builder.setView(view)

        // Отримуємо посилання на Spinners з макету
        val selectingTypeSpinner = view.findViewById<Spinner>(R.id.SelectingType)
        val selectingCategorySpinner = view.findViewById<Spinner>(R.id.SelectingCategory)

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
            // Код для підтвердження, наприклад, збереження вибраних даних або виконання дії
        }

        builder.setNegativeButton("Скасувати") { dialog, which ->
            dialog.dismiss()
        }

        return builder.create()
    }
}

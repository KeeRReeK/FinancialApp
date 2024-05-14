package com.example.financialapp.DB

import java.util.Calendar

class DatabaseClearHelper {
    fun clearDatabaseIfSixthOfMonth() {
        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        if (dayOfMonth == 6) {
            // Викликаємо метод очищення бази даних
            clearDatabase()
        }
    }

    private fun clearDatabase() {
        // Очистіть базу даних тут
    }
}
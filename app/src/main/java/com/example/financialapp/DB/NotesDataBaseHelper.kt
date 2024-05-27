package com.example.financialapp.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDataBaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "financialapp.db"
        private const val DATABASE_VERSION = 4
        private const val TABLE_NAME = "allrecords"
        private const val COLUMN_ID = "id"
        private const val COLUMN_COUNT = "count"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_COUNT INTEGER, $COLUMN_TYPE TEXT, $COLUMN_CATEGORY TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DATE  TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_COUNT, note.count)
            put(COLUMN_TYPE, note.type)
            put(COLUMN_CATEGORY, note.category)
            put(COLUMN_DESCRIPTION, note.description)
            put(COLUMN_DATE, note.date)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllIncomeNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'Дохід'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
                val count = cursor.getDouble(cursor.getColumnIndex(COLUMN_COUNT))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                val note = Note(id, count, "Дохід", category, description, date)
                notesList.add(note)
            } while (cursor.moveToPrevious())
        }
        cursor.close()
        db.close()
        return notesList
    }

    @SuppressLint("Range")
    fun getAllExpenseNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'Витрати'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
                val count = cursor.getDouble(cursor.getColumnIndex(COLUMN_COUNT))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                val note = Note(id, count, "Витрати", category, description, date)
                notesList.add(note)
            } while (cursor.moveToPrevious())
        }
        cursor.close()
        db.close()
        return notesList
    }

    @SuppressLint("Range")
    fun getAllNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
                val count = cursor.getDouble(cursor.getColumnIndex(COLUMN_COUNT))
                val type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                val note = Note(id, count, type, category, description, date)
                notesList.add(note)
            } while (cursor.moveToPrevious())
        }
        cursor.close()
        db.close()
        return notesList
    }


    @SuppressLint("Range")
    fun getTotalCount(): Double {
        var totalCount = 0.0
        val db = this.readableDatabase
        // Отримати суму всіх доходів
        val incomeQuery = "SELECT SUM($COLUMN_COUNT) AS totalIncome FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'Дохід'"
        val incomeCursor = db.rawQuery(incomeQuery, null)
        if (incomeCursor.moveToFirst()) {
            val totalIncome = incomeCursor.getDouble(incomeCursor.getColumnIndex("totalIncome"))
            totalCount += totalIncome
        }
        incomeCursor.close()

        // Отримати суму всіх витрат
        val expenseQuery = "SELECT SUM($COLUMN_COUNT) AS totalExpense FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'Витрати'"
        val expenseCursor = db.rawQuery(expenseQuery, null)
        if (expenseCursor.moveToFirst()) {
            val totalExpense = expenseCursor.getDouble(expenseCursor.getColumnIndex("totalExpense"))
            totalCount += (totalExpense * -1)
        }
        expenseCursor.close()

        db.close()
        return totalCount
    }

    fun updateNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_COUNT, note.count)
            put(COLUMN_TYPE, note.type)
            put(COLUMN_CATEGORY, note.category)
            put(COLUMN_DESCRIPTION, note.description)
            put(COLUMN_DATE, note.date)
        }
        var whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun deleteNote(noteId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }


    fun getNoteById(noteId: Int): Note {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val count = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COUNT))
        val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
        val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

        cursor.close()
        db.close()
        return Note(id, count, type, category, description, date)
    }

    fun getTotalCategory(category: String): Double {
        var totalCount = 0.0
        val db = this.readableDatabase

        // Отримати суму всіх доходів та витрат для вказаної категорії
        val query = "SELECT SUM($COLUMN_COUNT) AS total, $COLUMN_TYPE FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ? GROUP BY $COLUMN_TYPE"
        val cursor = db.rawQuery(query, arrayOf(category))
        if (cursor.moveToFirst()) {
            do {
                val total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                if (type == "Дохід") {
                    totalCount += total
                } else if (type == "Витрати") {
                    totalCount -= total
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return totalCount
    }

    fun clearAllData() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }

}
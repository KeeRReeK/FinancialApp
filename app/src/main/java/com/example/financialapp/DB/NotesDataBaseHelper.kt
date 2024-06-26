package com.example.financialapp.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDataBaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "financialapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allrecords"
        private const val COLUMN_ID = "id"
        private const val COLUMN_COUNT = "count"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_DESCRIPTION = "description"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_COUNT INTEGER, $COLUMN_TYPE TEXT, $COLUMN_CATEGORY TEXT, $COLUMN_DESCRIPTION TEXT)"
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
        if (cursor.moveToLast()) { // Починаємо з останнього запису
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
                val count = cursor.getDouble(cursor.getColumnIndex(COLUMN_COUNT))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val note = Note(id, count, "Дохід", category, description)
                notesList.add(note)
            } while (cursor.moveToPrevious()) // Переходимо до попереднього запису
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
                val note = Note(id, count, "Витрати", category, description)
                notesList.add(note)
            } while (cursor.moveToPrevious())
        }
        cursor.close()
        db.close()
        return notesList
    }

}
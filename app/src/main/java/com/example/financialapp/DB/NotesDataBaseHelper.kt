package com.example.financialapp.DB

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
}
package com.example.livedata.Database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask

/**
 * Project Name LiveData
 * Created by MOHD SALEEM
 * Date  10/11/18.
 * Desc :
 */
@Database(entities = [Note::class], version = 1 , exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao


    companion object {
        @Volatile
        var noteDataInstance: NoteDatabase? = null

        @Synchronized
        fun getNoteDatabaseInstance(context: Context): NoteDatabase? {
            if (noteDataInstance == null) {

                noteDataInstance = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java, "note_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomDatabaseCallback)
                        .build()
            }
            return noteDataInstance
        }

        var roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                DummyNoteData(noteDataInstance).execute()
            }
        }
    }

    class DummyNoteData : AsyncTask<Void, Void, Void> {
        private var noteDao: NoteDao;

        constructor(noteDatabase: NoteDatabase?) {
            noteDao = noteDatabase!!.noteDao()
        }

        override fun doInBackground(vararg params: Void?): Void? {

            noteDao.insert(note = Note("Title 1 ", "Description 1", 1))
            noteDao.insert(note = Note("Title 2 ", "Description 2", 2))
            noteDao.insert(note = Note("Title 3 ", "Description 3", 3))

            return null
        }
    }

}
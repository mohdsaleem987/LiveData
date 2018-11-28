package com.example.livedata

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import com.example.livedata.Database.Note
import com.example.livedata.Database.NoteDao
import com.example.livedata.Database.NoteDatabase

/**
 * Project Name LiveData
 * Created by MOHD SALEEM
 * Date  10/11/18.
 * Desc :
 */
class DatabaseRepository {


    private val noteDao : NoteDao
    private var allNotes : LiveData<List<Note>>

    constructor(application: Application)
    {
        var database : NoteDatabase = NoteDatabase.getNoteDatabaseInstance(application)!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNote()
    }
    fun insert(note : Note)
    {
        InsertNoteAsyncTask(noteDao).execute(note)
    }
    fun update(note : Note)
    {
        UpdateNoteAsyncTask(noteDao).execute(note)

    }
    fun delete(note : Note)
    {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }
    fun deleteAll()
    {
        DeleteAllNoteAsyncTask(noteDao).execute()

    }
    fun getNotesList() : LiveData<List<Note>>
    {
        Log.d("check","Database Repository get Notes Lists")
        return allNotes
    }

    class InsertNoteAsyncTask : AsyncTask<Note,Void,Void>
    {
        private var noteDao : NoteDao

        constructor(noteDao: NoteDao) : super() {
            this.noteDao = noteDao
        }

        override fun doInBackground(vararg params: Note?): Void? {
            noteDao.insert(note = params[0] as Note)
            return null
        }

    }
    class UpdateNoteAsyncTask : AsyncTask<Note,Void,Void>
    {
        private var noteDao : NoteDao

        constructor(noteDao: NoteDao) : super() {
            this.noteDao = noteDao
        }

        override fun doInBackground(vararg params: Note?): Void? {
          val update =  noteDao.update(note = params[0] as Note)
            Log.d("check","update " + update)
            return null
        }

    }
    class DeleteNoteAsyncTask : AsyncTask<Note,Void,Void>
    {
        private var noteDao : NoteDao

        constructor(noteDao: NoteDao) : super() {
            this.noteDao = noteDao
        }

        override fun doInBackground(vararg params: Note?): Void? {
            noteDao.delete(note = params[0] as Note)
            return null
        }

    }
    class DeleteAllNoteAsyncTask : AsyncTask<Void,Void,Void>
    {
        private var noteDao : NoteDao

        constructor(noteDao: NoteDao) : super() {
            this.noteDao = noteDao
        }

        override fun doInBackground(vararg params: Void?): Void?{
            noteDao.deleteAllNotes()
            return null
        }
    }
}
package com.example.livedata

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.livedata.Database.Note

/**
 * Project Name LiveData
 * Created by MOHD SALEEM
 * Date  10/11/18.
 * Desc :
 */
class NoteViewModel : AndroidViewModel{

    var databaseRepository: DatabaseRepository
    var allNotes :  LiveData<List<Note>>


   public constructor(application: Application):super(application)
    {
        Log.d("check","Note view model constructor")
        databaseRepository= DatabaseRepository(application)
        allNotes = databaseRepository.getNotesList()
    }

    fun insert(note: Note) {
        databaseRepository.insert(note)
    }

    fun update(note: Note) {
        databaseRepository.update(note)
    }

    fun delete(note: Note) {
        databaseRepository.delete(note)
    }

    fun deleteAllNotes() {
        databaseRepository.deleteAll()
    }
    fun getTotalNotes() : LiveData<List<Note>>
    {
        Log.d("check","Note view model get All Notes")
        return allNotes
    }
}
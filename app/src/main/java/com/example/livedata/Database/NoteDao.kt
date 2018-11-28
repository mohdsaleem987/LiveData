package com.example.livedata.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


/**
 * Project Name LiveData
 * Created by MOHD SALEEM
 * Date  10/11/18.
 * Desc :
 */
@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("Delete From note_table")
    fun deleteAllNotes()

    @Query("Select * From note_table ORDER BY priority DESC")
    fun getAllNote() : LiveData<List<Note>>

}
package com.example.livedata.Database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Project Name LiveData
 * Created by MOHD SALEEM
 * Date  10/11/18.
 * Desc :
 */
@Entity(tableName = "note_table")
class Note {

    @NotNull
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0


    var title : String
    var desc : String
    var priority : Int

    constructor(title: String, desc: String, priority: Int) {
        this.title = title
        this.desc = desc
        this.priority = priority
    }
}
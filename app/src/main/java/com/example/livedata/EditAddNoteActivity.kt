package com.example.livedata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class EditAddNoteActivity : AppCompatActivity() {

    companion object {

        val ID: String = "id"
        val TITLE: String = "title"
        val DESC: String = "desc"
        val PRIORITY: String = "priority"
    }
    lateinit var editTextTitel: EditText
    lateinit var editTextDesc: EditText
    lateinit var numberPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTextTitel = findViewById(R.id.et_text_title)
        editTextDesc = findViewById(R.id.et_text_description)
        numberPicker = findViewById(R.id.np_priority)

        numberPicker.minValue = 1
        numberPicker.maxValue = 10

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

        if(intent.hasExtra(ID))
        {
            setTitle("Edit Notes")
            editTextTitel.setText(intent.getStringExtra(TITLE))
            editTextDesc.setText(intent.getStringExtra(DESC))
            numberPicker.value = (intent.getIntExtra(PRIORITY,0))

        }else {
            setTitle("Add Notes")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.add_note_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.save_note -> saveNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {

        var Title : String = editTextTitel.text.toString()
        var Desc  : String = editTextDesc.text.toString()
        var priority : Int = numberPicker.value

        if(Title.trim().isEmpty() || Desc.trim().isEmpty())
        {
            Toast.makeText(this,
                    "Please Enter insert title and desc",Toast.LENGTH_SHORT).show()
            return
        }

        var data : Intent = Intent()
        data.putExtra(TITLE,Title)
        data.putExtra(DESC,Desc)
        data.putExtra(PRIORITY,priority)

        val id = intent.getIntExtra(ID,-1)
        if(id != -1){
            data.putExtra(ID,id)
        }

        setResult(Activity.RESULT_OK,data)
        finish()
    }
}

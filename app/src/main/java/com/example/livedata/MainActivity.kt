package com.example.livedata

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.livedata.Database.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NoteAdapter.OnItemClick {

    val ADD_NOTE_REQUEST : Int = 1
    val EDIT_NOTE_REQUEST : Int = 2


    val TITLE : String = "title"
    val DESC : String = "desc"
    val PRIORITY : String = "priority"


   private lateinit var noteViewModel : NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_note.layoutManager = LinearLayoutManager(this)
        rv_note.setHasFixedSize(true)

        var nodeAdapter = NoteAdapter()
        rv_note.adapter = nodeAdapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getTotalNotes().observe(this, object : Observer<List<Note>>
        {
            override fun onChanged(t: List<Note>?) {
                if (t != null) {
                    Log.d("check","on changed" + t.size)
                    nodeAdapter.setNotes(t)
                }
            }
        })

        var floatingActionButton : FloatingActionButton = findViewById(R.id.button_add_note)
        floatingActionButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //Your code here
                var requestIntent : Intent = Intent(this@MainActivity, EditAddNoteActivity::class.java)
                startActivityForResult(requestIntent,ADD_NOTE_REQUEST)
            }
        })

//        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT )
        {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder,
                                p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder : RecyclerView.ViewHolder, p1: Int) {
            noteViewModel.delete(nodeAdapter.getNotes(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(rv_note)

        nodeAdapter.setClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK)
        {
            var title = data!!.getStringExtra(TITLE)
            var desc= data!!.getStringExtra(DESC)
            var prio= data!!.getIntExtra(PRIORITY,0)

            var note = Note(title,desc,prio)

            noteViewModel.insert(note)

            Toast.makeText(this,"Note Added ", Toast.LENGTH_SHORT).show()
        }else  if(requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK){
            val id = data!!.getIntExtra(EditAddNoteActivity.ID,-1)
            Log.d("check ","edit note request id "+ id)
            if(id == -1){
                Toast.makeText(this,"Note can't be updated", Toast.LENGTH_SHORT).show()
            }else{
                var title = data!!.getStringExtra(TITLE)
                var desc= data!!.getStringExtra(DESC)
                var prio= data!!.getIntExtra(PRIORITY,0)

                var note = Note(title,desc,prio)
                note.id = id

                noteViewModel.update(note)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){

            R.id.delete_all_notes -> {noteViewModel.deleteAllNotes()
                Toast.makeText(this,"All Notes are Deleted ", Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun OnItemClick(note: Note) {

        var requestIntent : Intent = Intent(this@MainActivity, EditAddNoteActivity::class.java)
        requestIntent.putExtra(EditAddNoteActivity.ID,note.id)
        requestIntent.putExtra(EditAddNoteActivity.TITLE,note.title)
        requestIntent.putExtra(EditAddNoteActivity.DESC,note.desc)
        requestIntent.putExtra(EditAddNoteActivity.PRIORITY,note.priority)

        startActivityForResult(requestIntent,EDIT_NOTE_REQUEST)
    }
}


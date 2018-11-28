package com.example.livedata

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.livedata.Database.Note

/**
 * Project Name LiveData
 * Created by MOHD SALEEM
 * Date  10/11/18.
 * Desc :
 */
class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    var note : List<Note> = ArrayList<Note>()

    lateinit public var onItemClick: OnItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView =LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item,parent,false)


        return NoteHolder(this as NoteAdapter,itemView)
    }

    override fun getItemCount(): Int {

        return note.size
    }

    fun setNotes(notes : List<Note>)
    {
        this.note = notes
        notifyDataSetChanged()
    }

    fun getNotes(position : Int): Note
    {
        return note.get(position)
    }
    fun setClickListener(itemClickListener: OnItemClick)
    {
        onItemClick = itemClickListener
    }


    override fun onBindViewHolder(noteHolder: NoteHolder, position: Int) {

        val  currentNote = note.get(position)
        noteHolder.tvTitle.text = currentNote.title
        noteHolder.tvPriority.text = currentNote.priority.toString()
        noteHolder.tvDesc.text = currentNote.desc
    }


    class NoteHolder : RecyclerView.ViewHolder,View.OnClickListener
    {
        val tvTitle : TextView
        val tvDesc : TextView
        val tvPriority : TextView
        var mContext : NoteAdapter

        constructor(context: NoteAdapter, itemView: View) : super(itemView)
        {
            mContext = context
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvDesc = itemView.findViewById(R.id.tv_desc)
            tvPriority = itemView.findViewById(R.id.tv_priority)

            itemView.setOnClickListener(this as NoteHolder)
        }

        override fun onClick(v: View?) {

            var position : Int = adapterPosition
            (mContext).onItemClick!!.OnItemClick(mContext.note.get(position))
        }
    }

    interface OnItemClick
    {
        fun OnItemClick(note : Note)
    }
}
package com.hank.guess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hank.guess.data.Record
import kotlinx.android.synthetic.main.row_record.view.*

class RecordAdapter(var records : List<Record>) : RecyclerView.Adapter<RecordViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_record,parent,false))
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.counterText.text = records.get(position).count.toString()
        holder.nicknameText.text = records.get(position).nickname
    }

}

class RecordViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val nicknameText = view.record_nickname
    val counterText = view.record_counter
}
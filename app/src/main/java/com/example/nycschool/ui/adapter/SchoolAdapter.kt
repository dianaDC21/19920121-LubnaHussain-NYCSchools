package com.example.nycschool.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschool.R
import com.example.nycschool.model.NYCSchoolResponseItem

/**
 * RecyclerView adapter for displaying dynamic list of schools
 */
class SchoolAdapter(private val listener: OnSchoolItemClickListener) :
    RecyclerView.Adapter<SchoolAdapter.MyViewHolder>() {
    private var schoolData = mutableListOf<NYCSchoolResponseItem>()

    fun setMovieList(schoolData: List<NYCSchoolResponseItem>) {
        this.schoolData = schoolData.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.school_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.schoolName.text = schoolData[position].school_name
    }

    override fun getItemCount(): Int {
        return schoolData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var schoolName: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onSchoolItemClick(absoluteAdapterPosition)
        }
    }

    interface OnSchoolItemClickListener {
        fun onSchoolItemClick(position: Int)
    }
}


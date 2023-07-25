package com.example.nycschool.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschool.R
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.utils.Constants.Companion.DB_REFERENCE

class SchoolAdapter : RecyclerView.Adapter<SchoolAdapter.MyViewHolder>() {
    //todo use data binding
    var schoolData = mutableListOf<NYCSchoolResponseItem>()

    fun setMovieList(schoolData: List<NYCSchoolResponseItem>) {
        this.schoolData = schoolData.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.school_item, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.schoolName.text = schoolData[position].school_name
    }

    override fun getItemCount(): Int {
        return schoolData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var schoolName: TextView = itemView.findViewById(R.id.textView)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val bundle = Bundle()
            bundle.putString(DB_REFERENCE, schoolData[adapterPosition].dbn)
            itemView.findNavController().navigate(R.id.action_NYCSchoolFragment_to_NYCSchoolDetail, bundle)
        }
    }
}


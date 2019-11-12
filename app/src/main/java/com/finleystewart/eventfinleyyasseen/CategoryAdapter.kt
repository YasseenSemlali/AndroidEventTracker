package com.finleystewart.eventfinleyyasseen

import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.Adapter
import kotlinx.android.synthetic.main.layout_category.view.*
import kotlinx.android.synthetic.main.layout_event.view.*

class CategoryAdapter(private val categories: List<String>)  : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_category, parent,false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = categories[position]

        holder.view.name.text = event
        holder.view.setOnClickListener {
            Log.d("categories", "$event clicked")
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
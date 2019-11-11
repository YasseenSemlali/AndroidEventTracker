package com.finleystewart.eventfinleyyasseen

import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.Adapter
import com.finleystewart.eventfinleyyasseen.business.Event
import kotlinx.android.synthetic.main.layout_category.view.*
import kotlinx.android.synthetic.main.layout_event.view.*

class EventAdapter(private val events: List<Event>)  : RecyclerView.Adapter<EventAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_event, parent,false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]

        holder.view.shortDesc.text = event.shortDesc
        holder.view.date.text = event.date
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
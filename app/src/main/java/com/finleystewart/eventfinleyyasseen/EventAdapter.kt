package com.finleystewart.eventfinleyyasseen

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finleystewart.eventfinleyyasseen.business.Event
import kotlinx.android.synthetic.main.layout_event.view.*
import java.text.SimpleDateFormat

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
        holder.view.date.text = event.eventDate.toString()

        holder.view.setOnClickListener {
            val intent = Intent( holder.view.context, EventActivity::class.java)
            intent.putExtra("event", event)
            holder.view.context.startActivity(intent)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
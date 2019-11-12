package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finleystewart.eventfinleyyasseen.firebase.EventDAOImpl

class CurrentEventsActivity : Activity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.current_events_activity)

        val dao = EventDAOImpl()
        dao.loadCurrentEvents({
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = EventAdapter(it)
            }
            val DividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager(applicationContext).getOrientation())
            recyclerView.addItemDecoration(DividerItemDecoration)
        })
    }
}
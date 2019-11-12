package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.util.Log

class MyEventsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("AssignmentTwo","My Events Activity launched")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_events_activity)

        /*

        WIP

        val events : MutableCollection<Event> = DAO.loadUserEvents()

        viewManager = LinearLayoutManager(applicationContext)
        viewAdapter = EventAdapter(events)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        */
    }
}
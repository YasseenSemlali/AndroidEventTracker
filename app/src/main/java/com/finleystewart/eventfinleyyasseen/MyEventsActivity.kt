package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.EventDAOImpl
import com.finleystewart.eventfinleyyasseen.firebase.UserDAOImpl

class MyEventsActivity : Activity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("AssignmentTwo","My Events Activity launched")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_events_activity)

        val dao = UserDAOImpl()

        dao.loadUserEvents({
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = EventAdapter(it)
            }
        })
    }
}
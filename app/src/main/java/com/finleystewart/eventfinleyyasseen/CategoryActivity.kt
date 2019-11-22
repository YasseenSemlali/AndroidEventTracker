package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.EventDAOImpl

class CategoryActivity : Activity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)

        val dao = EventDAOImpl()

        var intent = intent
        val category = intent.getStringExtra("category")

        dao.loadEventsInCategory(category , {
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = EventAdapter(it)
            }
            val DividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager(applicationContext).getOrientation())
            recyclerView.addItemDecoration(DividerItemDecoration)
        })
    }
}
package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.UserDAOImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EventActivity : Activity() {

    val lateinit event : Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = intent

        event = intent.extras!!.get("event")

        val shortDesc = findViewById<TextView>(R.id.shortDesc)
        val longDesc = findViewById<TextView>(R.id.longDesc)
        val date = findViewById<TextView>(R.id.date)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val DAO = UserDAOImpl()

        fab.setOnClickListener {
            if(DAO.isOnUserList(event)) {
                DAO.removeFromUserList(event)
                toast("Favorited")
            } else {
                DAO.addToUserList(event)
                toast("Unfavorited")
            }
        }

    }

}

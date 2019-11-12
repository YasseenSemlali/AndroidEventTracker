package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import android.widget.Toast
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.UserDAOImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

class EventActivity : Activity() {

    private lateinit var event : Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = intent

        event = intent.extras!!.get("event") as Event

        val shortDesc = findViewById<TextView>(R.id.shortDesc)
        shortDesc.text = event.shortDesc

        val longDesc = findViewById<TextView>(R.id.longDesc)
        longDesc.text = event.longDesc

        val date = findViewById<TextView>(R.id.date)
        val format = SimpleDateFormat("yyyy/mm/dd")
        date.text = format.format(event.eventDate)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val DAO = UserDAOImpl()

        fab.setOnClickListener {
            var message = ""
            if(DAO.isOnUserList(event)) {
                DAO.removeFromUserList(event)
                message = "Favourited"
            } else {
                DAO.addToUserList(event)
                message = "Unfavourited"
            }
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

}

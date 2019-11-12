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
        setContentView(R.layout.event_activity)

        var intent = intent

        val shortDesc = findViewById<TextView>(R.id.shortDesc)
        shortDesc.text = intent.getStringExtra("shortdesc")

        val longDesc = findViewById<TextView>(R.id.longDesc)
        longDesc.text = intent.getStringExtra("longdesc")

        val date = findViewById<TextView>(R.id.date)
        date.text = intent.getStringExtra("date")

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val DAO = UserDAOImpl()

        DAO.isOnUserList(event.key, fun(result: Boolean) {
            fab.setOnClickListener {
                if (result) {
                    DAO.removeUserEvent(event.key)
                    val toast = Toast.makeText(this, "Unfavorited", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    DAO.addUserEvent(event)
                    val toast = Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        })

    }

}

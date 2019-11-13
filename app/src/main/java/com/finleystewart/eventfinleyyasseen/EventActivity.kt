package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.FirebaseConstants
import com.finleystewart.eventfinleyyasseen.firebase.UserDAOImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

class EventActivity : Activity() {

    private lateinit var event : Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        var intent = intent

        event = intent.getSerializableExtra("event") as Event

        val shortDesc = findViewById<TextView>(R.id.shortDesc)
        shortDesc.text = event.shortDesc

        val longDesc = findViewById<TextView>(R.id.longDesc)
        longDesc.text = event.longDesc

        val date = findViewById<TextView>(R.id.date)
        date.text = event.eventDate.toString()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val DAO = UserDAOImpl()

        DAO.isOnUserList(event.key, fun(result: Boolean) {
            if(result) {
                fab.setImageResource(R.drawable.ic_remove_white_24dp)
            }
        })

        fab.setOnClickListener {
            DAO.isOnUserList(event.key, fun(result: Boolean) {
                Log.d(FirebaseConstants.FIREBASE_TAG, "isOnUserList: $result")
                if (result) {
                    DAO.removeUserEvent(event.key)
                    fab.setImageResource(R.drawable.ic_add_white_24dp)
                    val toast = Toast.makeText(this, "Unfavorited", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    DAO.addUserEvent(event)
                    fab.setImageResource(R.drawable.ic_remove_white_24dp)
                    val toast = Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT)
                    toast.show()
                }
            })

        }


    }

}

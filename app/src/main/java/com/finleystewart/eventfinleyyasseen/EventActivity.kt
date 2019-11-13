package com.finleystewart.eventfinleyyasseen

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.FirebaseConstants
import com.finleystewart.eventfinleyyasseen.firebase.UserDAOImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.layout_category.view.*
import java.text.SimpleDateFormat

class EventActivity : Activity() {

    private lateinit var event : Event

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        var intent = intent

        event = intent.getSerializableExtra("event") as Event

        val categoryIcon = findViewById<ImageView>(R.id.category)
        when(event.category.trim()) {
            "Health Services" -> categoryIcon.setImageDrawable(getDrawable(R.drawable.category_health))
            "Academic" -> categoryIcon.setImageDrawable(getDrawable(R.drawable.category_academic))
            "Campus Life & Leadership" -> categoryIcon.icon.setImageDrawable(getDrawable(R.drawable.category_campus_life))
            "Earth Week" -> categoryIcon.icon.setImageDrawable(getDrawable(R.drawable.category_earth_week))
            "Peace" -> categoryIcon.icon.setImageDrawable(getDrawable(R.drawable.category_peace))
            else -> categoryIcon.icon.setImageDrawable(getDrawable(R.drawable.category_default))
        }

        val shortDesc = findViewById<TextView>(R.id.shortDesc)
        shortDesc.text = event.shortDesc

        val longDesc = findViewById<TextView>(R.id.longDesc)
        longDesc.text = event.longDesc

        val date = findViewById<TextView>(R.id.date)
        date.text = event.eventDate.toString()

        val eventDuration = findViewById<TextView>(R.id.duration)
        eventDuration.text =
            """${getString(R.string.duration)}: ${event.eventDuration} ${getString(R.string.hours)}"""

        val timesRepeated = findViewById<TextView>(R.id.timesRepeated)
        timesRepeated.text = "${getString(R.string.repeated)}: ${event.timesRepeated} ${getString(R.string.times)}"

        val repeatType = findViewById<TextView>(R.id.repeatType)
        repeatType.text = """${getString(R.string.repeatType)}: ${event.repeatType}"""

        val siteUrl = findViewById<TextView>(R.id.siteUrl)
        siteUrl.text = """${getString(R.string.siteUrl)} ${event.siteUrl.toString()}"""

        val eventUrl = findViewById<TextView>(R.id.eventUrl)
        eventUrl.text = """${getString(R.string.eventUrl)} ${event.eventUrl.toString()}"""


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

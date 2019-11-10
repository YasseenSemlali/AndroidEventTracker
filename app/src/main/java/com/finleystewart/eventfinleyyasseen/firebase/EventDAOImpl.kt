package com.finleystewart.eventfinleyyasseen.firebase

import android.content.res.Resources
import android.util.Log
import com.finleystewart.eventfinleyyasseen.MainActivity
import com.finleystewart.eventfinleyyasseen.R
import com.finleystewart.eventfinleyyasseen.firebase.model.Event
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EventDAOImpl {
    private var db: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.FIREBASE_EVENTS)

    fun addEvent(event: Event) {
        val key = db.push().key!!

        db.child(key).setValue(event)
        Log.d(FirebaseConstants.FIREBASE_TAG, "Event added: " + event)
    }

    fun getAllEvents(events: MutableCollection<Event> =  ArrayList<Event>()): MutableCollection<Event> {
        db.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                events.clear()

                snapshot.children.mapNotNullTo(events) {
                    it.getValue<Event>(Event::class.java)
                }
            }
        })

        return events
    }
}
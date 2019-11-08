package com.finleystewart.eventfinleyyasseen.firebase

import android.util.Log
import com.finleystewart.eventfinleyyasseen.firebase.model.Event
import com.google.firebase.database.*

class FirebaseDAOImpl {
    private var db: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FirebaseTables.FIREBASE_EVENTS)

    fun addEvent(event: Event) {
        val key = db.child(FirebaseTables.FIREBASE_EVENTS).push().key
        event.key = key

        db.setValue(event)

        Log.d("firebase", "Event added: " + event)
    }

    fun getAllEvents(): List<Event> {
        val events: ArrayList<Event> = ArrayList<Event>();

        db.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                children.forEach {
                    events.add(it as Event);
                }
            }

        });

        return events
    }
}
package com.finleystewart.eventfinleyyasseen.firebase

import android.util.Log
import com.finleystewart.eventfinleyyasseen.firebase.model.Event
import com.google.firebase.database.FirebaseDatabase

class FirebaseDAOImpl {
    private var db = FirebaseDatabase.getInstance().reference

    fun addEvent(event: Event) {
        val key = db.child(FirebaseTables.FIREBASE_EVENTS).push().key
        event.key = key

        db.child(FirebaseTables.FIREBASE_EVENTS).setValue(event)

        Log.d("firebase", "Event added: " + event)
    }
}
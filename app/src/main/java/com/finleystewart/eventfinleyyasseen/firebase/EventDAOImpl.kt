package com.finleystewart.eventfinleyyasseen.firebase

import android.util.Log
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.model.DBEvent
import com.google.firebase.database.*

class EventDAOImpl {
    private val eventDB: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.FIREBASE_EVENTS)

    fun addEvent(event: DBEvent) {
        val key = eventDB.push().key!!

        eventDB.child(key).setValue(event)
        Log.d(FirebaseConstants.FIREBASE_TAG, "Event added: $event")
    }

    fun loadEvents(events: MutableCollection<Event> =  mutableSetOf()): MutableCollection<Event> {
        return loadEventsInCategory(null)
    }

    fun loadEventsInCategory(category: String?, events: MutableCollection<Event> =  mutableSetOf()): MutableCollection<Event> {
        events.clear()

        eventDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {
                    val event = it.getValue<DBEvent>(DBEvent::class.java)?.toEvent(it.key!!)

                    if(category == null || category == event?.category) {
                        Log.v(FirebaseConstants.FIREBASE_TAG, "Event loaded: " + event?.toString())
                        events.add(event!!)
                    }
                }
            }
        })

        return events
    }

    fun loadCategories(categories: MutableCollection<String> =  mutableSetOf()): MutableCollection<String> {
        categories.clear()

        eventDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {
                    val event = it.getValue<DBEvent>(DBEvent::class.java)?.toEvent(it.key!!)

                    if(!categories.contains(event?.category)) {
                        Log.v(FirebaseConstants.FIREBASE_TAG, "Category loaded: " + event?.category.toString())
                        categories.add(event!!.category)
                    }
                }
            }
        })

        return categories
    }
}
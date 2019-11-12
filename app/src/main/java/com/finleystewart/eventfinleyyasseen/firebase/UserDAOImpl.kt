package com.finleystewart.eventfinleyyasseen.firebase

import android.util.Log
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.model.DBEvent
import com.finleystewart.eventfinleyyasseen.firebase.model.DBUser
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserDAOImpl {
    private val eventDB: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.FIREBASE_EVENTS)
    private val userDb: DatabaseReference = FirebaseDatabase.getInstance(FirebaseApp.getInstance("user")).reference.child(FirebaseConstants.FIREBASE_USERS)
    private val auth = FirebaseAuth.getInstance(FirebaseApp.getInstance("user"))

    fun addUser(user: DBUser) {
        userDb.child(auth.currentUser!!.uid).setValue(user)
        Log.d(FirebaseConstants.FIREBASE_TAG, "User added: $user")
    }

    fun addUserEvent(callback: (Event) -> Unit) {

    }

    fun loadUserEvents(callback: (events: List<Event>) -> Unit, events: MutableCollection<Event> =  mutableSetOf()): MutableCollection<Event> {
        events.clear()

        userDb.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val size = snapshot.childrenCount
                var i = 0.toLong()

                snapshot.child(auth.currentUser!!.uid).child("favourited").children.forEach {
                    eventDB.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
                            i++
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            i++

                            val key: String = it.getValue(String::class.java)!!

                            val event: Event? = snapshot.child(key).getValue(DBEvent::class.java)?.toEvent(key)

                            Log.d(FirebaseConstants.FIREBASE_TAG, "User Event loaded: " + event?.toString())

                            event?.let {
                                events.add(it)
                            }

                            if(i == size) {
                                callback(events.toList())
                            }
                        }
                    })
                }
            }
        })

        return events
    }
}
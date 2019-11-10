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
    private val auth = FirebaseAuth.getInstance()

    fun addUser(user: DBUser) {
        userDb.child(auth.currentUser!!.uid).setValue(user)
        Log.d(FirebaseConstants.FIREBASE_TAG, "User added: " + user)
    }

    fun loadUserEvents(events: MutableCollection<Event> =  mutableSetOf()): MutableCollection<Event> {
        events.clear()

        userDb.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.child(auth.currentUser!!.uid).child("favourited").children.forEach {
                    eventDB.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val key: String = it.getValue(String::class.java)!!

                            val event: Event? = snapshot.child(key).getValue(DBEvent::class.java)?.toEvent(key)

                            Log.d(FirebaseConstants.FIREBASE_TAG, "User Event loaded: " + event?.toString())

                            event?.let {
                                events.add(it)
                            }
                        }
                    })
                }
            }
        })

        return events
    }
}
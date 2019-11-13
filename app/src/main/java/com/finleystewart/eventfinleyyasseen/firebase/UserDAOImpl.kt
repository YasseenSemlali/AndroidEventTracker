package com.finleystewart.eventfinleyyasseen.firebase

import android.util.Log
import com.finleystewart.eventfinleyyasseen.business.Event
import com.finleystewart.eventfinleyyasseen.firebase.model.DBEvent
import com.finleystewart.eventfinleyyasseen.firebase.model.DBUser
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener



class UserDAOImpl {
    private val eventDB: DatabaseReference = FirebaseDatabase.getInstance(FirebaseApp.getInstance("secondary")).reference.child(FirebaseConstants.FIREBASE_EVENTS)
    private val userDb: DatabaseReference = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.FIREBASE_USERS)
    private val auth = FirebaseAuth.getInstance()

    fun addUser(user: DBUser) {
        userDb.child(auth.currentUser!!.uid).setValue(user)
        Log.d(FirebaseConstants.FIREBASE_TAG, "User added: $user")
    }

    fun addUserEvent(event: Event, callback: (event: Event) -> Unit = {}) {
        userDb.child(auth.currentUser!!.uid).child("favourited").child(event.key).setValue(true)
        Log.d(FirebaseConstants.FIREBASE_TAG, "User event added: $event")

        callback(event)
    }

    fun removeUserEvent(key : String, callback: (key: String) -> Unit = {}) {
        userDb.child(auth.currentUser!!.uid).child("favourited").child(key).removeValue()
        Log.d(FirebaseConstants.FIREBASE_TAG, "User event removed: $key")

        callback(key)
    }

    fun isOnUserList(key : String, callback: (result: Boolean) -> Unit = {}) {
        userDb.child(auth.currentUser!!.uid).child("favourited").child(key).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.value?.let {
                        Log.d("firebase", it.toString())
                    }
                    Log.d("firebase", snapshot.toString())
                    callback(snapshot.value != null)
                }
            })
    }

    fun loadUserEvents(callback: (events: List<Event>) -> Unit = {}, events: MutableCollection<Event> =  mutableSetOf()): MutableCollection<Event> {
        events.clear()

        userDb.child(auth.currentUser!!.uid).child("favourited").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val size = snapshot.childrenCount
                var i = 0.toLong()

                snapshot.children.forEach {
                    val key = it.key
                    Log.v(FirebaseConstants.FIREBASE_TAG, "Favourite key: $key")

                    eventDB.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            i++
                            Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(item in snapshot.children!!) {
                                if(item.key == key) {
                                    val event = item.getValue(DBEvent::class.java)?.toEvent(item.key!!)
                                    Log.v(FirebaseConstants.FIREBASE_TAG, "Favourite event loaded: " + event.toString())

                                    event?.let {
                                        events.add(it)
                                        i++
                                    }
                                }

                                if(i == size) {
                                    callback(events.toList())
                                }
                            }
                        }

                    })
                }


                /*
                snapshot.child("favourited").children.forEach {
                    eventDB.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            Log.e(FirebaseConstants.FIREBASE_TAG, error!!.message)
                            i++
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            i++

                            val key: String = it.key!!

                            val event: Event? = snapshot.child(key).getValue(DBEvent::class.java)?.toEvent(key)

                            Log.v(FirebaseConstants.FIREBASE_TAG, "User Event loaded: " + event?.toString())

                            event?.let {
                                events.add(it)
                            }

                            if(i == size) {
                                callback(events.toList())
                            }
                        }
                    })
                }
                */
            }
        })

        return events
    }
}
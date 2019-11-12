package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.util.Log

class CurrentEventsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("AssignmentTwo","Current Events Activity launched")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.current_events_activity)
    }
}
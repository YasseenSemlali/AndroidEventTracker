package com.finleystewart.eventfinleyyasseen

import android.app.Activity
import android.os.Bundle
import android.util.Log

class AboutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("AssignmentTwo","About Activity launched")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
    }
}
package com.finleystewart.eventfinleyyasseen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)

        val intent: Intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("https://www.dawsoncollege.qc.ca/"))

        // Sets link to WWW option
        menu.findItem(R.id.item4).setIntent(
            intent
        )

        return true;
    }

}

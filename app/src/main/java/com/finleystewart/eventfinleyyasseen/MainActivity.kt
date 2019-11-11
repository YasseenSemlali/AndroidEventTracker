package com.finleystewart.eventfinleyyasseen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.finleystewart.eventfinleyyasseen.firebase.EventDAOImpl
import com.finleystewart.eventfinleyyasseen.firebase.FirebaseConstants
import com.finleystewart.eventfinleyyasseen.firebase.UserDAOImpl
import com.finleystewart.eventfinleyyasseen.firebase.model.DBEvent
import com.finleystewart.eventfinleyyasseen.firebase.model.DBUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import java.text.DateFormat
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val d = SimpleDateFormat("yyyy-MM-dd HH:mm")
        Log.d("temp", d.parse("2019-11-13 11:00").time.toString())
        Log.d("temp", d.parse("2019-11-15 11:00").time.toString())
        Log.d("temp", d.parse("2019-11-18 11:00").time.toString())
        Log.d("temp", d.parse("2019-11-21 11:00").time.toString())
        Log.d("temp", d.parse("2019-11-29 11:00").time.toString())
        Log.d("temp", d.parse("2019-12-01 11:00").time.toString())
        Log.d("temp", d.parse("2019-12-13 11:00").time.toString())
        Log.d("temp", d.parse("2019-12-22 11:00").time.toString())
        Log.d("temp", d.parse("2019-12-24 11:00").time.toString())
        Log.d("temp", d.parse("2019-13-24 11:00").time.toString())
        Log.d("temp", d.parse("2020-01-01 11:00").time.toString())
        Log.d("temp", d.parse("2020-01-01 11:00").time.toString())
        Log.d("temp", d.parse("2020-01-01 11:00").time.toString())
        Log.d("temp", d.parse("2020-01-02 11:00").time.toString())
        Log.d("temp", d.parse("2020-01-29 11:00").time.toString())

        this.initUserDB()

        this.loginMainDB()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)

        val intent: Intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://www.dawsoncollege.qc.ca/")

        // Sets link to WWW option
        menu.findItem(R.id.item4).setIntent(
            intent
        )

        return true
    }

    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if(account != null) {
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 0)
        }
    }

    private fun googleLogout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if(account != null) {
            FirebaseAuth.getInstance(FirebaseApp.getInstance("user")).signOut()
        }
    }

    private fun initUserDB() {
        val options = FirebaseOptions.Builder()
            .setApplicationId("com.finleystewart.eventfinleyyasseen")
            .setApiKey("AIzaSyCObFHoVy_q9spFBmqb8R2flITZXzRxVV4")
            .setDatabaseUrl("https://eventfinleyyassenuser.firebaseio.com/")
            .build()

        FirebaseApp.initializeApp(this, options, "user")
    }

    private fun loginMainDB() {
        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        if(currentUser == null) {
            auth.signInWithEmailAndPassword(FirebaseConstants.USERNAME, FirebaseConstants.PASSWORD)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(FirebaseConstants.FIREBASE_TAG, "App wide login success")
                        currentUser = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(FirebaseConstants.FIREBASE_TAG, "App wide login faiilure")
                        Toast.makeText(baseContext, "Authentication failed, most app functionality will not work",
                            Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
        } else {

        }
    }

}

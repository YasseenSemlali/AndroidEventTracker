package com.finleystewart.eventfinleyyasseen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
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






class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                // Start About Activity
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}

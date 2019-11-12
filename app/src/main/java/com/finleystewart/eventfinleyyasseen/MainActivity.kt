package com.finleystewart.eventfinleyyasseen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finleystewart.eventfinleyyasseen.firebase.EventDAOImpl
import com.finleystewart.eventfinleyyasseen.firebase.FirebaseConstants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initUserDB()
        this.loginMainDB()

        val dao = EventDAOImpl()
        dao.loadCategories({
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = CategoryAdapter(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()

        this.googleLogin()
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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 0) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(FirebaseConstants.FIREBASE_AUTH_TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        googleAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(FirebaseConstants.FIREBASE_AUTH_TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Google authentication failed, most app functionality will not work",
                        Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }

    private fun googleLogin() {
        googleAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance("user"))
        var currentUser = auth.currentUser

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("237115235891-jb2v389j76u84m8f97o4ar4h20dd77pi.apps.googleusercontent.com")
            .requestEmail()
            .build()

        if(currentUser == null) {
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = mGoogleSignInClient.signInIntent

            Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "Opening google activity")
            startActivityForResult(signInIntent, 0)
        } else {
            Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "Didn't log in, Google account already logged in")
        }
    }

    private fun googleLogout() {
        googleAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance("user"))
        var currentUser = auth.currentUser

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        if(currentUser == null) {
            Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "Logging out user")
            FirebaseAuth.getInstance(FirebaseApp.getInstance("user")).signOut()
        } else {
            Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "Didn't log out, Google account already logged in")
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
                        Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "App wide login success")
                        currentUser = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(FirebaseConstants.FIREBASE_AUTH_TAG, "App wide login faiilure")
                        Toast.makeText(baseContext, "Authentication failed, most app functionality will not work",
                            Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
        } else {
            Log.d(FirebaseConstants.FIREBASE_AUTH_TAG, "Already logged in")
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

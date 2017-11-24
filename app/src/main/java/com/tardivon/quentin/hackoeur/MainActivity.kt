package com.tardivon.quentin.hackoeur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fbAuth = FirebaseAuth.getInstance()
        var fbUser = fbAuth.currentUser

        if (fbUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        } else {
            startActivity(Intent(this, MyEventsActivity::class.java))
        }

    }

    fun signOut(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, SignInActivity::class.java))
    }

}

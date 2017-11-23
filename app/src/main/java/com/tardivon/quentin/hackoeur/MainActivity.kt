package com.tardivon.quentin.hackoeur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
            var userName = fbUser!!.displayName

            if (fbUser!!.photoUrl != null) {
                var photoUrl = fbUser!!.photoUrl.toString()
            }
        }

    }

}

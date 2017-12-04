package com.tardivon.quentin.hackoeur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger


class MainActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fbAuth = FirebaseAuth.getInstance()
        var fbUser = fbAuth.currentUser

        initFirebase()


        if (fbUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        tabLayout.addTab(tabLayout.newTab().setText("Feed"))
        tabLayout.addTab(tabLayout.newTab().setText("Friend"))
        tabLayout.addTab(tabLayout.newTab().setText("My Events"))

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val adapter = TabPagerAdapter(supportFragmentManager, tabLayout.tabCount)

        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // TODO
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // TODO
            }
        }) //Don't forget to use removeOnTabSelectedListener
    }

    fun signOut(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, SignInActivity::class.java))
    }

    private fun initFirebase() {
        //init firebase
        FirebaseApp.initializeApp(this.applicationContext)

        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)

        //get reference to our db
        databaseReference = FirebaseDatabase.getInstance().reference
    }


}

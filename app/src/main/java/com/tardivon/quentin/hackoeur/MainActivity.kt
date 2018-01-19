package com.tardivon.quentin.hackoeur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import android.view.MenuItem
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseReference = FirebaseDatabase.getInstance().reference
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        var fbAuth = FirebaseAuth.getInstance()
        var fbUser = fbAuth.currentUser

        if (fbUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            return
        }

        addUid(fbUser!!.uid, fbUser!!.email)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        tabLayout.addTab(tabLayout.newTab().setText("Feed"))
        tabLayout.addTab(tabLayout.newTab().setText("Friends"))
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

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signout -> {
                signOut()
                return true
            }
            R.id.create_event -> {
                startActivity(Intent(this, CreateActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, SignInActivity::class.java))
    }

    fun addUid(uid: String?, email: String?) {
        databaseReference!!.child("Users").child(uid).child("email").setValue(email)
        databaseReference!!.child("Users").child(uid).child("uid").setValue(uid)
    }


}

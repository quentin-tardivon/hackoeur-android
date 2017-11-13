package com.tardivon.quentin.hackoeur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_event_list.*

class EventListActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        initFirebase()
        createFirebaseListener()

    }

    private fun initFirebase() {
        //init firebase
        FirebaseApp.initializeApp(applicationContext)

        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)

        //get reference to our db
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    private fun createFirebaseListener() {
       val postListener = object: ValueEventListener {
           override fun onDataChange(dataSnapshot: DataSnapshot) {
               val toReturn: ArrayList<Event> = ArrayList()

               for (data in dataSnapshot.children) {
                   val eventData = data.getValue(Event::class.java)

                   val event = eventData?.let { it }?: continue

                   toReturn.add(event)
               }

               setupAdapter(toReturn)
           }

           override fun onCancelled(databaseError: DatabaseError) {
               //log error
           }
       }
        databaseReference?.child("events")?.addValueEventListener(postListener)
    }

    private fun setupAdapter(data: ArrayList<Event>){
        val linearLayoutManager = LinearLayoutManager(this)
        eventListActivityRecyclerView.layoutManager = linearLayoutManager
        eventListActivityRecyclerView.adapter = EventAdapter(data) {
            Toast.makeText(this, "${it.name} clicked", Toast.LENGTH_SHORT).show()
        }

        //scroll to bottom
        eventListActivityRecyclerView.scrollToPosition(data.size - 1)
    }
}

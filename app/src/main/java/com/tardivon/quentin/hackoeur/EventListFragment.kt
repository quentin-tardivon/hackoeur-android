package com.tardivon.quentin.hackoeur

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import android.support.v4.view.ViewCompat.setNestedScrollingEnabled
import android.support.v7.widget.RecyclerView




class EventListFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_event_list, container, false)

        createFirebaseListener()

        return view
    }


    private fun createFirebaseListener() {
        val postListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val toReturn: ArrayList<Event> = ArrayList()
                val keys: ArrayList<String> = ArrayList()

                for (data in dataSnapshot.children) {
                    val eventData = data.getValue(Event::class.java)
                    val event = eventData?.let { it }?: continue

                    keys.add(data.key)
                    toReturn.add(event)
                }

                setupAdapter(toReturn,keys)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //log error
            }
        }
        (activity as MainActivity).databaseReference?.child("Events")?.addValueEventListener(postListener)
    }

    private fun setupAdapter(data: ArrayList<Event>, keys:ArrayList<String>){
        val linearLayoutManager = LinearLayoutManager(this.activity)
        eventListActivityRecyclerView.layoutManager = linearLayoutManager
            eventListActivityRecyclerView.adapter = EventAdapter(data) {
                val intent: Intent = Intent(this.context, EventDescriptionActivity::class.java).putExtra("event", it)
                intent.putExtra("lat", it.locationGPS!!.lat!!)
                intent.putExtra("lng", it.locationGPS!!.lng!!)
                val i = data.indexOf(it)
                intent.putExtra("key", keys[i])
                startActivity(intent)
        }

    }


}
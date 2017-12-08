package com.tardivon.quentin.hackoeur

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_event_list.*


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
        (activity as MainActivity).databaseReference?.child("Events")?.addValueEventListener(postListener)
    }

    private fun setupAdapter(data: ArrayList<Event>){
        val linearLayoutManager = LinearLayoutManager(this.activity)
        eventListActivityRecyclerView.layoutManager = linearLayoutManager
        eventListActivityRecyclerView.adapter = EventAdapter(data) {
            Toast.makeText(this.activity, "${it.name} clicked", Toast.LENGTH_SHORT).show()
        }

        //scroll to bottom
        eventListActivityRecyclerView.scrollToPosition(data.size - 1)
    }


}
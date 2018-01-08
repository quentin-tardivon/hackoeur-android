package com.tardivon.quentin.hackoeur

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.GoogleMap
import android.R.attr.label
import android.net.Uri
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * A simple [Fragment] subclass.
 */
class MyEventsFragment : Fragment(), OnMapReadyCallback {

    var mMapView: MapView? = null
    var rootView: View? = null
    private var mMap: GoogleMap? = null
    var myEvents =mutableListOf<Event>()
    var keys = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.fragment_my_events, container, false)
        myEvents.clear()
        val postListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (data in dataSnapshot.children) {
                    val eventData = data.getValue(Event::class.java)

                    val event = eventData?.let { it }?: continue
                    keys.add(data.key)
                    myEvents.add(event)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //log error
            }
        }
        (activity as MainActivity).databaseReference?.child("Events")?.addValueEventListener(postListener)

        //code used to initiate the google map on the fragment
        mMapView = rootView!!.findViewById<MapView>(R.id.mapView) as MapView
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.onResume()
        mMapView!!.getMapAsync(this)
        MapsInitializer.initialize(activity.applicationContext)
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //loop to create all map markers for event and set the on MapMarkerClick function
        for (i in myEvents.indices) {
            //create marker
            val marker = LatLng(myEvents[i].locationGPS!!.lat!!, myEvents[i].locationGPS!!.lng!!)
            val googleMarker = mMap!!.addMarker(MarkerOptions().position(marker).title(myEvents[i].name!!)) as Marker
            mMap!!.setOnInfoWindowClickListener(OnInfoWindowClickListener { marker ->
                //when clicking the information window for a mapmarker open location in google maps
                val uriBegin = "geo:" + marker.position.latitude.toString() + "," + marker.position.longitude.toString()
                val query = marker.position.latitude.toString() + "," + marker.position.longitude.toString() + "(" + marker.title + ")"
                val encodedQuery = Uri.encode(query)
                val uriString = uriBegin + "?q=" + encodedQuery
                val uri = Uri.parse(uriString)
                val intent = Intent(android.content.Intent.ACTION_VIEW, uri)
                startActivity(intent)
            })
        }

        val startinglocation = LatLng(53.348, -6.262) //hard coded location to be in Dublin
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(startinglocation, 12.0f)); //set the zoom function to be in dublin

        //Populate the list of event with the data stored in the arrays
            val adapter = MyEventsAdapter(this.activity, myEvents.toTypedArray(), mMap as GoogleMap)
            val lv = rootView!!.findViewById(R.id.MyEventList) as ListView
            lv.adapter = adapter
            //add a onClickListener to the list
            lv.setClickable(true)
            lv.onItemClickListener = AdapterView.OnItemClickListener { adapter, activity_main, i, l ->
                val intent: Intent = Intent(this.context, EventDescriptionActivity::class.java).putExtra("event", myEvents[i])
                intent.putExtra("lat", myEvents[i].locationGPS!!.lat!!)
                intent.putExtra("lng", myEvents[i].locationGPS!!.lng!!)
                intent.putExtra("key", keys[i])
                startActivity(intent)

            }
    }

}// Required empty public constructor

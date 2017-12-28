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




/**
 * A simple [Fragment] subclass.
 */
class MyEventsFragment : Fragment(), OnMapReadyCallback {

    var mMapView: MapView? = null
    var rootView: View? = null
    private var mMap: GoogleMap? = null
    // remove this code later
    val title = arrayOf("test","test2","test3","tesetetstststs");
    val picture = arrayOf(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background);
    val lat = arrayOf(53.346760,53.347349,53.334999,53.344364)
    val long = arrayOf(-6.2287331,-6.2390542,-6.2285614,-6.2389684)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.fragment_my_events, container, false)

        //code used to initiate the google map on the fragment
        mMapView = rootView!!.findViewById<MapView>(R.id.mapView) as MapView
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.onResume()
        mMapView!!.getMapAsync(this);
        MapsInitializer.initialize(activity.applicationContext);
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //loop to create all map markers for event and set the on MapMarkerClick function
        for(i in lat.indices) {
            //create marker
            val marker = LatLng(lat[i], long[i])
            val googleMarker = mMap!!.addMarker(MarkerOptions().position(marker).title(title[i])) as Marker
            mMap!!.setOnInfoWindowClickListener(OnInfoWindowClickListener { marker ->
                //when clicking the information window for a mapmarker open location in google maps
                     val uriBegin = "geo:"+ marker.position.latitude.toString() + ","+  marker.position.longitude.toString()
                     val query =  marker.position.latitude.toString() + ","+  marker.position.longitude.toString()+"("+marker.title+")"
                     val encodedQuery = Uri.encode(query)
                     val uriString = uriBegin + "?q=" + encodedQuery
                     val uri = Uri.parse(uriString)
                     val intent = Intent(android.content.Intent.ACTION_VIEW, uri)
                     startActivity(intent)
            })
        }

        val startinglocation = LatLng(53.348, -6.262) //hard coded location to be in Dublin
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(startinglocation,12.0f)); //set the zoom function to be in dublin

        //Populate the list of event with the data stored in the arrays
        val adapter = MyEventsAdapter(this.activity ,title,picture, lat, long, mMap as GoogleMap)
        val lv = rootView!!.findViewById<ListView>(R.id.MyEventList) as ListView
        lv.adapter = adapter;

        //add a onClickListener to the list
        lv.setClickable(true)
        lv.onItemClickListener = AdapterView.OnItemClickListener { adapter, activity_main, i, l ->
            Toast.makeText(this.context,"Position Clicked:"+" "+i, Toast.LENGTH_SHORT).show()
            val changePage = Intent(this.context,EventDescriptionActivity::class.java)
            startActivity(changePage)
        }
    }

}// Required empty public constructor

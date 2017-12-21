package com.tardivon.quentin.hackoeur

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


/**
 * A simple [Fragment] subclass.
 */
class MyEventsFragment : Fragment(), OnMapReadyCallback {

    var mMapView: MapView? = null
    var rootView: View? = null
    private var mMap: GoogleMap? = null
    val title = arrayOf("test","test2","test3","tesetetstststs");
    val picture = arrayOf(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background);
    val lat = arrayOf(53.346760,53.347349,53.334999,53.344364)
    val long = arrayOf(-6.2287331,-6.2390542,-6.2285614,-6.2389684)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.fragment_my_events, container, false)

        mMapView = rootView!!.findViewById(R.id.mapView) as MapView
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.onResume()
        mMapView!!.getMapAsync(this);
        MapsInitializer.initialize(activity.applicationContext);
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        for(i in lat.indices) {
            val sydney = LatLng(lat[i], long[i])
            mMap!!.addMarker(MarkerOptions().position(sydney).title(title[i]))
        }
        val startinglocation = LatLng(53.348, -6.262)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(startinglocation,12.0f));
        val adapter = MyEventsAdapter(this.activity ,title,picture, lat, long, mMap as GoogleMap)
        val lv = rootView!!.findViewById<ListView>(R.id.MyEventList) as ListView
        lv.adapter = adapter;
        lv.setClickable(true)
        lv.onItemClickListener = AdapterView.OnItemClickListener { adapter, activity_main, i, l ->
            Toast.makeText(this.context,"Position Clicked:"+" "+i, Toast.LENGTH_SHORT).show()
        }
    }

}// Required empty public constructor

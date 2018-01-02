package com.tardivon.quentin.hackoeur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MyEventsActivity : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap
    val title = arrayOf("test","test2","test3","test4");
    val picture = arrayOf(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background);
    val lat = arrayOf(53.346760,53.347349,53.334999,53.344364)
    val long = arrayOf(-6.2287331,-6.2390542,-6.2285614,-6.2389684)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_events)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.MyEventMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        for(i in lat.indices) {
            val sydney = LatLng(lat[i], long[i])
            mMap.addMarker(MarkerOptions().position(sydney).title(title[i]))
        }
        val startinglocation = LatLng(53.348, -6.262)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startinglocation,12.0f));
        //val adapter = MyEventsAdapter(this,title,picture,lat,long,mMap)
        val lv = findViewById<ListView>(R.id.MyEventList) as ListView;
       // lv.adapter = adapter;
        lv.setClickable(true)
        lv.onItemClickListener = AdapterView.OnItemClickListener { adapter, activity_main, i, l ->
            Toast.makeText(this,"Position Clicked:"+" "+i, Toast.LENGTH_SHORT).show()
        }
    }
}

package com.tardivon.quentin.hackoeur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_event_description.*
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class EventDescriptionActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var event: Event? = null
    private var lat: Double? = null
    private var lng: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_description)
        event = intent.getParcelableExtra<Event>("event")
        lat = intent.getDoubleExtra("lat",0.0)
        lng = intent.getDoubleExtra("lng",0.0)

        this.eventDate.text = event!!.date
        this.eventName.text = event!!.name
        this.eventLocation.text = event!!.location
        this.eventDescription.text = event!!.description

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.eventMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val marker = LatLng(lat!!, lng!!)
        mMap!!.addMarker(MarkerOptions().position(marker).title(event!!.name))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 12.0f));

    }
}

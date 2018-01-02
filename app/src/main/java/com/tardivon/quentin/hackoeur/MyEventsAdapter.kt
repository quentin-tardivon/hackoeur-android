package com.tardivon.quentin.hackoeur


import android.app.Activity
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.*

class MyEventsAdapter(private val context: Activity, private val events : Array<Event>, private val imgid: Array<Int>, private val mMap: GoogleMap)// TODO Auto-generated constructor stub
    : ArrayAdapter<Event>(context, R.layout.my_event_list_layout, events) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.my_event_list_layout, null, true)

        val imageView = rowView.findViewById<View>(R.id.EventListImage) as ImageView
        val extratxt = rowView.findViewById<View>(R.id.EventListText) as TextView
        val eventbutton = rowView.findViewById<View>(R.id.ButtonMyEvent) as Button
        imageView.setImageResource(imgid[position])
        extratxt.text = events[position].name
        eventbutton.setOnClickListener {
           val sydney = LatLng(events[position].locationGPS!!.lat!!, events[position].locationGPS!!.lng!!)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));
        }
        return rowView
    }
}
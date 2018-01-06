package com.tardivon.quentin.hackoeur


import android.app.Activity
import android.graphics.BitmapFactory
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_event_description.*
import java.io.File

class MyEventsAdapter(private val context: Activity, private val events : Array<Event>, private val mMap: GoogleMap)// TODO Auto-generated constructor stub
    : ArrayAdapter<Event>(context, R.layout.my_event_list_layout, events) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.my_event_list_layout, null, true)
        val storageRef = FirebaseStorage.getInstance().getReference()
        val imageView = rowView.findViewById<View>(R.id.EventListImage) as ImageView
        val extratxt = rowView.findViewById<View>(R.id.EventListText) as TextView
        val eventbutton = rowView.findViewById<View>(R.id.ButtonMyEvent) as Button
        extratxt.text = events[position].name
        eventbutton.setOnClickListener {
           val sydney = LatLng(events[position].locationGPS!!.lat!!, events[position].locationGPS!!.lng!!)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));
        }
        if (events[position].imgId != null) {
            val riversRef = storageRef.child("Photos/" + events[position]!!.imgId)
            val localFile = File.createTempFile("images", "jpg")
            riversRef.getFile(localFile)
                    .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        imageView.setImageBitmap(bitmap)
                    }).addOnFailureListener(OnFailureListener {
                // Handle failed download
            })

        }
        else
        {
            imageView.setImageResource(R.drawable.ic_launcher_background)
        }
        return rowView
    }
}
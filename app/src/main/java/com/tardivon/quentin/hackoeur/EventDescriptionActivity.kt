package com.tardivon.quentin.hackoeur

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLog
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_event_description.*
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.event_item.view.*
import java.io.File


class EventDescriptionActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var event: Event? = null
    private var lat: Double? = null
    private var lng: Double? = null
    private var eventKey: String? =null
    private var user_uid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val storageRef = FirebaseStorage.getInstance().getReference()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_description)
        user_uid = FirebaseAuth.getInstance().currentUser!!.uid
        event = intent.getParcelableExtra<Event>("event")
        lat = intent.getDoubleExtra("lat",0.0)
        lng = intent.getDoubleExtra("lng",0.0)
        eventKey = intent.getStringExtra("key")
        this.eventDate.text = event!!.date
        this.eventName.text = event!!.name
        this.eventLocation.text = event!!.location
        this.eventDescription.text = event!!.description
        if (event!!.imgId != null) {
            val riversRef = storageRef.child("Photos/" + event!!.imgId)
            val localFile = File.createTempFile("images", "jpg")
            riversRef.getFile(localFile)
                    .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        this.imageView.setImageBitmap(bitmap)
                    }).addOnFailureListener(OnFailureListener {
                // Handle failed download
            })

        }
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.eventMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setParticipateButtonText() // call the function to set the text of the participate button
        // Set the OnClick Listener for the button for the subscribe / unsubscribe function
        this.participateButton.setOnClickListener {
            // We must first get and update the event to either subscribe(add the user id to the event) or unsubscribe(remove the user id from the event)
            FirebaseDatabase.getInstance().getReference("Events").child(eventKey).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError?) {
                    println("Error")
                }

                override fun onDataChange(snapshot: DataSnapshot?) {
                    val event = snapshot!!.getValue(Event::class.java)
                   //check if the event has any registed user
                     val NullCheck = event!!.registeredUsers.toString() as String?
                    if (NullCheck == "null")
                    {
                        var EmptyList = mutableListOf<String>()
                        EmptyList.add(user_uid!!)
                        event!!.registeredUsers = EmptyList
                        updateEvent(event)
                        participateButton.text = "UnSubscribe"
                    }
                    else
                    {
                        if (event!!.registeredUsers!!.contains(user_uid!!)) {
                           //unsubcribe
                            event!!.registeredUsers!!.remove(user_uid!!)
                            updateEvent(event)
                            participateButton.text = "Subscribe"
                        } else {
                            //subscribe
                            event!!.registeredUsers!!.add(user_uid!!)
                            updateEvent(event)
                            participateButton.text = "UnSubscribe"
                        }
                    }
                }
            })

            // We must now get and update the user to either subscribe(add the event id to the user) or unsubscribe(remove the event id from the user)
            FirebaseDatabase.getInstance().getReference("Users").child(user_uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError?) {
                    println("Error")
                }
                override fun onDataChange(snapshot: DataSnapshot?) {
                    val user = snapshot!!.getValue(User::class.java)
                   // check if the user has any other subscrition
                    val NullCheck = user!!.eventList.toString() as String?
                    if (NullCheck == "null")
                    {
                        var EmptyList = mutableListOf<String>()
                        EmptyList.add(eventKey!!)
                        user!!.eventList = EmptyList
                        updateUser(user)
                    }
                    else
                    {
                        if (user!!.eventList!!.contains(eventKey!!)) {
                            //unsubscribe
                            user!!.eventList!!.remove(eventKey!!)
                            updateUser(user)
                        } else {
                            //subscribe
                            user!!.eventList!!.add(eventKey!!)
                            updateUser(user)
                        }
                    }
                }
            })
        } // end of the button onclick
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val marker = LatLng(lat!!, lng!!)
        mMap!!.addMarker(MarkerOptions().position(marker).title(event!!.name))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 12.0f));

    }

    //function needed due to async firebase call
    fun updateEvent(event : Event)
    {
        FirebaseDatabase.getInstance().getReference("Events").child(eventKey).setValue(event!!)
    }


    //function needed due to async firebase call
    fun updateUser(user : User)
    {
        FirebaseDatabase.getInstance().getReference("Users").child(user_uid).setValue(user!!)
    }

    //function to set the button text when the function is create
    fun setParticipateButtonText()
    {

        FirebaseDatabase.getInstance().getReference("Events").child(eventKey).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                println("Error")
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                val event = snapshot!!.getValue(Event::class.java)
                val NullCheck = event!!.registeredUsers.toString() as String?
                if (NullCheck == "null") {
                    participateButton.text = "Subscribe"
                }
                else
                {
                    if (event!!.registeredUsers!!.contains(user_uid!!))
                    {
                        participateButton.text = "UnSubscribe"
                    }
                    else
                    {
                        participateButton.text = "Subscribe"
                    }
                }
            }
        })
    }
}

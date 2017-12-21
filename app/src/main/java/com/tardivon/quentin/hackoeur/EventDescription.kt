package com.tardivon.quentin.hackoeur

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;*/
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso

import java.io.InputStream

class EventDescription : AppCompatActivity(), OnMapReadyCallback {
    private var database: FirebaseDatabase? = null
    private val mAuth: FirebaseAuth? = null
    private val mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val userID: String? = null
    private val mStorage: StorageReference? = null
    internal var mRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Eventss")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_description)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //databaseEvents= FirebaseDatabase.getInstance().getReference("Eventss");
        //databaseEvents= FirebaseDatabase.getInstance().getReference().child("-L-7-hYZN7w9ZnDeuLfz");
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                showData(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun addCountofPeople(view: View, datasnapshot: DataSnapshot) {
        val checked = (view as ToggleButton).isChecked
        if (checked) {
            val peoplegoing = findViewById<View>(R.id.textView8) as TextView
            for (eventSnapShot in datasnapshot.children) {
                database = FirebaseDatabase.getInstance()
                mRef = database!!.reference
                val id = mRef.key
                val eventss = Eventss()
                eventss.count = (eventSnapShot.child("-L-7-hYZN7w9ZnDeuLfz").getValue(Eventss::class.java)!!.count)
                val count = eventss.count
                var countp = Integer.parseInt(count)
                countp = countp + 1
                peoplegoing.setText(countp)
                mRef.child("Clients").child("dhglklw8").child("events").child("-L-7-hYZN7w9ZnDeuLfz").push()
                mRef.child("Clients").child("dhglklw8").child("events").child("-L-7-hYZN7w9ZnDeuLfz").child("eventId").push().setValue("-L-7-hYZN7w9ZnDeuLfz")
                mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("clients").child("dhglklw8").push()
                mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("clients").child("dhglklw8").child("eventId").push().setValue("dhglklw8")
                mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("count").setValue(countp)

            }
        } else {
            val peoplegoing = findViewById<View>(R.id.textView8) as TextView
            for (eventSnapShot in datasnapshot.children) {
                val eventss = Eventss()
                eventss.count = eventSnapShot.child("-L-7-hYZN7w9ZnDeuLfz").getValue(Eventss::class.java)!!.count
                val count = eventss.count
                var countp = Integer.parseInt(count)
                countp = countp - 1
                peoplegoing.setText(countp)
                mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("count").setValue(countp)
                mRef.child("Clients").child("dhglklw8").child("events").child("-L-7-hYZN7w9ZnDeuLfz").removeValue()
                mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("clients").child("dhglklw8").removeValue()
            }
        }

    }

    /* @Override
    protected void onStart() {
        super.onStart();


        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TextView datetime=(TextView) findViewById(R.id.textView2);
                TextView location=(TextView) findViewById(R.id.textView4);
                TextView nameofevent=(TextView) findViewById(R.id.textView);
                TextView description=(TextView) findViewById(R.id.textView10);
                System.out.println(dataSnapshot.getValue());
               // Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();
                for(DataSnapshot eventSnapshot: dataSnapshot.getChildren()){


                    Eventss eve=eventSnapshot.getValue(Eventss.class);
                    String day=eve.eventday;
                    String month=eve.eventmonth;
                    String year=eve.eventyear;
                    String locations=eve.eventlocation;
                    location.setText(locations);
                    datetime.setText(day+"/"+month+"/"+year);

                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/
    /* @GlideModule
   public class MyAppGlideModule extends AppGlideModule {

       @Override
       public void registerComponents(Context context, Glide glide, Registry registry) {
           // Register FirebaseImageLoader to handle StorageReference
           registry.append(StorageReference.class, InputStream.class,
                   new FirebaseImageLoader.Factory());
       }
   }*/
    private fun showData(dataSnapshot: DataSnapshot) {
        // mRef=FirebaseDatabase.getInstance().getReference("Eventss");
        for (eventSnapShot in dataSnapshot.child("Eventss").children) {

            mRef = FirebaseDatabase.getInstance().getReference("hackoeur-android-master").child("Eventss")
            val id = mRef.key
            val mImageView = findViewById<View>(R.id.imagebtn) as ImageView
            val datetime = findViewById<View>(R.id.textView2) as TextView
            val location = findViewById<View>(R.id.textView4) as TextView
            val nameofevent = findViewById<View>(R.id.textView) as TextView
            val description = findViewById<View>(R.id.textView10) as TextView
            val peoplegoing = findViewById<View>(R.id.textView8) as TextView
            val hostedby = findViewById<View>(R.id.textView6) as TextView

            val eventss = Eventss()
            val clients = Clients()
            eventss.eventday = eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventday
            eventss.eventmonth = eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventmonth
            eventss.eventyear = eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventyear
            eventss.eventname= eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventname
            eventss.eventlocation =eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventlocation
            eventss.eventdescription =eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventdescription
            eventss.count = eventSnapShot.child(id).getValue(Eventss::class.java)!!.count
            eventss.eventhour =eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventhour
            eventss.eventmin =eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventmin
            eventss.clientId =eventSnapShot.child(id).getValue(Eventss::class.java)!!.clientId
            eventss.eventImage =eventSnapShot.child(id).getValue(Eventss::class.java)!!.eventImage



            Toast.makeText(this@EventDescription, "Upload Done", Toast.LENGTH_SHORT).show()
            Log.v("Test", "image load")
            //Log.d(TAG,"showData:day"+eventss.getEventday());
            val day = eventss.eventday
            val image = eventss.eventImage
            //UploadTask.TaskSnapshot taskSnapshot;
            // Uri downloadUri=taskSnapshot.getDownloadUrl();
            Picasso.with(this@EventDescription).load(image).into(mImageView)
            /* Glide.with(this)
                   .load(image)
                   .into(mImageView);*/
            val client = eventss.clientId
            clients.clientEmail = dataSnapshot.child(client).getValue(Clients::class.java)!!.clientEmail
            val clientemail = clients.clientEmail
            val month = eventss.eventmonth
            val year = eventss.eventyear
            val name = eventss.eventname
            val loc = eventss.eventlocation
            val des = eventss.eventdescription
            val count = eventss.count
            val hour = eventss.eventhour
            val min = eventss.eventmin
            peoplegoing.text = count
            description.text = des
            location.text = loc
            nameofevent.text = name
            datetime.text = "$day/$month/$year\n$hour:$min hours"
            hostedby.text = clientemail

        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(-33.852, 151.211)
        googleMap.addMarker(MarkerOptions().position(sydney)
                .title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    companion object {
        private val TAG = "ViewDatabase"
    }
}
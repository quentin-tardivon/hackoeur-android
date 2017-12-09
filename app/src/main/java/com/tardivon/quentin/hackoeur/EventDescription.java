package com.tardivon.quentin.hackoeur;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;*/
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.Map;

public class EventDescription extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG="ViewDatabase";
    private FirebaseDatabase database;
private FirebaseAuth mAuth;
private FirebaseAuth.AuthStateListener mAuthListener;
private String userID;
    private StorageReference mStorage;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mRef=FirebaseDatabase.getInstance().getReference("Eventss");
        //databaseEvents= FirebaseDatabase.getInstance().getReference("Eventss");
        //databaseEvents= FirebaseDatabase.getInstance().getReference().child("-L-7-hYZN7w9ZnDeuLfz");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addCountofPeople(View view,DataSnapshot datasnapshot){
boolean checked=((ToggleButton)view).isChecked();
if(checked){
    TextView peoplegoing=(TextView) findViewById(R.id.textView8);
    for(DataSnapshot eventSnapShot :datasnapshot.getChildren()) {
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        String id=mRef.getKey();
        Eventss eventss = new Eventss();
        eventss.setCount(eventSnapShot.child("-L-7-hYZN7w9ZnDeuLfz").getValue(Eventss.class).getCount());
        String count=eventss.count;
        int countp=Integer.parseInt(count);
        countp=countp+1;
        peoplegoing.setText(countp);
        mRef.child("Clients").child("dhglklw8").child("events").child("-L-7-hYZN7w9ZnDeuLfz").push();
        mRef.child("Clients").child("dhglklw8").child("events").child("-L-7-hYZN7w9ZnDeuLfz").child("eventId").push().setValue("-L-7-hYZN7w9ZnDeuLfz");
        mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("clients").child("dhglklw8").push();
        mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("clients").child("dhglklw8").child("eventId").push().setValue("dhglklw8");
        mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("count").setValue(countp);

    }
}
else{
    TextView peoplegoing=(TextView) findViewById(R.id.textView8);
    for(DataSnapshot eventSnapShot :datasnapshot.getChildren()) {
        Eventss eventss = new Eventss();
        eventss.setCount(eventSnapShot.child("-L-7-hYZN7w9ZnDeuLfz").getValue(Eventss.class).getCount());
        String count=eventss.count;
        int countp=Integer.parseInt(count);
        countp=countp-1;
        peoplegoing.setText(countp);
        mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("count").setValue(countp);
        mRef.child("Clients").child("dhglklw8").child("events").child("-L-7-hYZN7w9ZnDeuLfz").removeValue();
        mRef.child("Eventss").child("-L-7-hYZN7w9ZnDeuLfz").child("clients").child("dhglklw8").removeValue();
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
   private void showData(DataSnapshot dataSnapshot){
      // mRef=FirebaseDatabase.getInstance().getReference("Eventss");
       for(DataSnapshot eventSnapShot:dataSnapshot.child("Eventss").getChildren())
       {

           mRef=FirebaseDatabase.getInstance().getReference("hackoeur-android-master").child("Eventss");
           String id=mRef.getKey();
           ImageView mImageView=(ImageView) findViewById(R.id.imagebtn);
           TextView datetime=(TextView) findViewById(R.id.textView2);
           TextView location=(TextView) findViewById(R.id.textView4);
           TextView nameofevent=(TextView) findViewById(R.id.textView);
           TextView description=(TextView) findViewById(R.id.textView10);
           TextView peoplegoing=(TextView) findViewById(R.id.textView8);
           TextView hostedby=(TextView) findViewById(R.id.textView6);

           Eventss eventss=new Eventss();
           Clients clients=new Clients();
           eventss.setEventday(eventSnapShot.child(id).getValue(Eventss.class).getEventday());
           eventss.setEventmonth(eventSnapShot.child(id).getValue(Eventss.class).getEventmonth());
           eventss.setEventyear(eventSnapShot.child(id).getValue(Eventss.class).getEventyear());
           eventss.setEventname(eventSnapShot.child(id).getValue(Eventss.class).getEventname());
           eventss.setEventlocation(eventSnapShot.child(id).getValue(Eventss.class).getEventlocation());
           eventss.setEventdescription(eventSnapShot.child(id).getValue(Eventss.class).getEventdescription());
           eventss.setCount(eventSnapShot.child(id).getValue(Eventss.class).getCount());
           eventss.setEventhour(eventSnapShot.child(id).getValue(Eventss.class).getEventhour());
           eventss.setEventmin(eventSnapShot.child(id).getValue(Eventss.class).getEventmin());
           eventss.setClientId(eventSnapShot.child(id).getValue(Eventss.class).getClientId());
           eventss.setEventImage(eventSnapShot.child(id).getValue(Eventss.class).getEventImage());



           Toast.makeText(EventDescription.this,"Upload Done",Toast.LENGTH_SHORT).show();
           Log.v("Test","image load");
           //Log.d(TAG,"showData:day"+eventss.getEventday());
           String day=eventss.eventday;
           String image=eventss.eventImage;
           //UploadTask.TaskSnapshot taskSnapshot;
          // Uri downloadUri=taskSnapshot.getDownloadUrl();
           Picasso.with(EventDescription.this).load(image).into(mImageView);
          /* Glide.with(this)
                   .load(image)
                   .into(mImageView);*/
           String client=eventss.clientId;
    clients.setClientEmail(dataSnapshot.child(client).getValue(Clients.class).getClientEmail());
           String clientemail=clients.ClientEmail;
           String month=eventss.eventmonth;
           String year=eventss.eventyear;
           String name=eventss.eventname;
           String loc=eventss.eventlocation;
           String des=eventss.eventdescription;
           String count=eventss.count;
           String hour=eventss.eventhour;
           String min=eventss.eventmin;
           peoplegoing.setText(count);
           description.setText(des);
           location.setText(loc);
nameofevent.setText(name);
           datetime.setText(day+"/"+month+"/"+year+"\n"+hour+":"+min+" hours");
          hostedby.setText(clientemail);

       }

   }

   public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
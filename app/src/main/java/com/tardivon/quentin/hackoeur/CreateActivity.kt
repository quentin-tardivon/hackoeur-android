package com.tardivon.quentin.hackoeur

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import com.google.android.gms.location.places.ui.PlacePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import android.view.MotionEvent
import android.view.View.OnTouchListener




class CreateActivity : AppCompatActivity(), View.OnClickListener {
    var databaseReference: DatabaseReference? = null

    private var eventName: EditText? = null
    private var description: EditText? = null

    private var createLocation: EditText? = null
    private var location: String? = null
    private var name: String? = null
    private var eventDescription: String? = null
    private var imageButton: Button? = null
    private var textview3: TextView? = null
    private var create: Button? = null
    private var textview: TextView? = null
    private var textview2: TextView? = null
    private var year_x: Int = 0
    private var month_x: Int = 0
    private var day_x: Int = 0
    private var hour_x: Int = 0
    private var minute_x: Int = 0
    private var time: String ? = null
    private var date: String? = null
    private var cal = Calendar.getInstance()
    private var id1: String ? = null
    private var mStorage: StorageReference? = null
    var users = mutableListOf<String>()
    var locationGPS: LatLng? = null
    var pictureId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        FirebaseApp.initializeApp(this)

        year_x=cal.get(Calendar.YEAR)
        month_x=cal.get(Calendar.MONTH)
        day_x=cal.get(Calendar.DAY_OF_MONTH)
        mStorage = FirebaseStorage.getInstance().reference //Used for Picture storage
        databaseReference = FirebaseDatabase.getInstance().getReference("Events") //Database reference
        val id = databaseReference?.push()?.key
        eventName = findViewById<View>(R.id.createeditText7) as EditText
        description = findViewById<View>(R.id.createeditText8) as EditText

        textview = findViewById<View>(R.id.textView) as TextView
        textview2=findViewById<View>(R.id.textView2) as TextView
        textview!!.setOnClickListener{ showDialog(CreateActivity.DIALOG_ID) }
        textview2!!.setOnClickListener{ showDialog(CreateActivity.DIALOG_ID1) }
        create = findViewById<View>(R.id.button2) as Button
        createLocation = findViewById<View>(R.id.createeditText9) as EditText
        textview3 = findViewById<View>(R.id.textView3) as TextView
        imageButton = findViewById<View>(R.id.Createbutton3) as Button
        imageButton!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,GALLERY_INTENT)
        }


        //Open Place Picker
        createLocation!!.setOnTouchListener(OnTouchListener { v, event ->
            if (MotionEvent.ACTION_UP == event.action)
            {
                val builder = PlacePicker.IntentBuilder()

                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
            }
                false
        })
        create!!.setOnClickListener(this)
    }

    //The onClick function for the create event button
    override fun onClick(view: View) {
        //get
        name = eventName!!.text.toString().trim { it <= ' ' }
        eventDescription = description!!.text.toString().trim { it <= ' ' }
        location = createLocation!!.text.toString().trim { it <= ' ' }

        //Ensure that the name, description and location fields are not null.
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(eventDescription) && !TextUtils.isEmpty(location)) {

            //create the event in the database
            val user_uid = FirebaseAuth.getInstance().currentUser!!.uid
            users.add(user_uid)
            val event = Event(name as String, eventDescription as String, location as String , date as String, time as String, locationGPS as LatLng, users, pictureId as String)
            val key = databaseReference!!.push().key
            databaseReference!!.child(key).setValue(event)
            //update the user to include the event
            FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(user_uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError?) {
                println("Error")
                }
                override fun onDataChange(snapshot: DataSnapshot?) {
                    val user = snapshot!!.child(user_uid).getValue(User::class.java)
                    //check to make if this is the first event
                    val nullcheck = user!!.eventList.toString() as String?
                    if (nullcheck == "null")
                    {
                        var myEvents = mutableListOf<String>()
                        myEvents.add(key)
                        user!!.eventList= myEvents
                    }
                    else
                    {
                        user!!.eventList!!.add(key)
                    }
                    FirebaseDatabase.getInstance().getReference("Users").child(user!!.uid).child("eventList").setValue(user!!.eventList)
                    createdEvent()
                 }
            })
        } else
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
    }

    private val dpickerListener = DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
        year_x = i
        month_x = i1+1
        day_x = i2
        date = day_x.toString() +"/" + month_x +"/"+year_x;
        textview!!.setText(date)
        Toast.makeText(this@CreateActivity, year_x.toString() + "/" + month_x + "/" + day_x, Toast.LENGTH_LONG).show()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        if (id == CreateActivity.DIALOG_ID1)
            return TimePickerDialog(this@CreateActivity, kTimePickerListener, hour_x, minute_x, true)

        return if (id == CreateActivity.DIALOG_ID) DatePickerDialog(this@CreateActivity, dpickerListener, year_x, month_x, day_x) else null
    }
    protected var kTimePickerListener: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
        hour_x = i
        minute_x = i1
        var str = StringBuffer()
        str = str.append(hour_x).append(":").append(minute_x)
        time = str.toString()
        textview2!!.setText(time)

        Toast.makeText(this@CreateActivity, hour_x.toString() + " : " + minute_x, Toast.LENGTH_LONG).show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CreateActivity.GALLERY_INTENT && resultCode == Activity.RESULT_OK) {
            pictureId = databaseReference?.push()?.key
            val uri = data?.data
            val filepath = mStorage!!.child("Photos").child(pictureId!!)
            textview3!!.setText("Uploading....")
            if (uri != null) {
                filepath.putFile(uri).addOnSuccessListener {
                    textview3!!.setText("Uploaded")
                    Toast.makeText(this@CreateActivity, "Upload Done. ", Toast.LENGTH_LONG).show() }
            }

        }
        else if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(data, this)
            val placename = String.format("%s", place.name)
            val toastMsg = String.format("%s", place.address)
            val location_name= placename+","+toastMsg
            locationGPS = LatLng(place.latLng.latitude, place.latLng.longitude)
            createLocation!!.setText(location_name)
            Toast.makeText(this, location_name , Toast.LENGTH_LONG).show()
        }
        else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }


    fun createdEvent()
    {
        Toast.makeText(this, "Event created Successfully",Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
    }
    companion object {
        internal val DIALOG_ID = 0
        internal val DIALOG_ID1 = 1
        val GALLERY_INTENT = 2
        val PLACE_PICKER_REQUEST = 1
    }
}


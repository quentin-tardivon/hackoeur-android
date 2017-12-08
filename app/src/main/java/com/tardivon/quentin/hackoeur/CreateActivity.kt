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


import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import com.google.android.gms.location.places.ui.PlacePicker
import android.widget.Toast
import com.google.android.gms.location.places.Place





class CreateActivity : AppCompatActivity(), View.OnClickListener {
    var databaseReference: DatabaseReference? = null

    internal var Event_name: EditText? = null
    internal var description: EditText?=null

    internal var create_location: EditText?=null
    internal var loc: String?=null
    internal var name: String?=null
    internal var Event_description: String?=null
    internal var Image: Button?=null
    internal var textview3: TextView?=null
    internal var create: Button?=null
    internal var textview: TextView?=null
    internal var textview2: TextView?=null
    internal var year_x: Int = 0
    internal var month_x: Int = 0
    internal var day_x: Int = 0
    internal var hour_x: Int = 0
    internal var minute_x: Int = 0
    internal var time: String ?=null
    internal var dat: String?=null
    internal var cal = Calendar.getInstance()
    internal var id1: String ?=null
    private var mStorage: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        FirebaseApp.initializeApp(this)

        year_x=cal.get(Calendar.YEAR)
        month_x=cal.get(Calendar.MONTH)
        day_x=cal.get(Calendar.DAY_OF_MONTH)
        mStorage = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Event")
        val id = databaseReference?.push()?.key
        Event_name = findViewById<View>(R.id.createeditText7) as EditText
        description = findViewById<View>(R.id.createeditText8) as EditText

        textview = findViewById<View>(R.id.textView) as TextView
        textview2=findViewById<View>(R.id.textView2) as TextView
        textview!!.setOnClickListener{ showDialog(CreateActivity.DIALOG_ID) }
        textview2!!.setOnClickListener{ showDialog(CreateActivity.DIALOG_ID1) }
        create = findViewById<View>(R.id.button2) as Button
        create_location = findViewById<View>(R.id.createeditText9) as EditText
        textview3 = findViewById<View>(R.id.textView3) as TextView
        Image = findViewById<View>(R.id.Createbutton3) as Button
        Image!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,GALLERY_INTENT)
        }



        create_location!!.setOnClickListener {

            val builder = PlacePicker.IntentBuilder()

            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
        }
        create!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        name = Event_name!!.text.toString().trim { it <= ' ' }
        Event_description = description!!.text.toString().trim { it <= ' ' }
        loc = create_location!!.text.toString().trim { it <= ' ' }


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(Event_description) && !TextUtils.isEmpty(loc)) {


            val event = createEvent(name as String, Event_description as String, loc as String , dat as String, time as String)

            databaseReference!!.child("Events").child(id1).setValue(event)
            Toast.makeText(this, "Event created Successfully",Toast.LENGTH_LONG).show()

        } else
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
    }
    private val dpickerListener = DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
        year_x = i
        month_x = i1+1
        day_x = i2
        dat=day_x.toString() +"/" + month_x +"/"+year_x;
        textview!!.setText(dat)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CreateActivity.GALLERY_INTENT && resultCode == Activity.RESULT_OK) {
            val id = databaseReference?.push()?.key
            id1=id
            val uri = data.data
            val filepath = mStorage!!.child("Photos").child(id!!).child(uri!!.lastPathSegment)
            textview3!!.setText("Uploading....")
            filepath.putFile(uri).addOnSuccessListener {
                textview3!!.setText("Uploaded")
                Toast.makeText(this@CreateActivity, "Upload Done. ", Toast.LENGTH_LONG).show() }

        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data, this)
                val placename = String.format("%s", place.name)
                 val toastMsg = String.format("%s", place.address)
                val location_name= placename+","+toastMsg

                create_location!!.setText(location_name)
                Toast.makeText(this, location_name , Toast.LENGTH_LONG).show()
            }
        }

    }


    companion object {
        internal val DIALOG_ID = 0
        internal val DIALOG_ID1 = 1
        val GALLERY_INTENT = 2
        val PLACE_PICKER_REQUEST = 1
    }
}


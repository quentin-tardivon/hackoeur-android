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
    private var dat: String? = null
    private var cal = Calendar.getInstance()
    private var id1: String ? = null
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



        createLocation!!.setOnClickListener {

            val builder = PlacePicker.IntentBuilder()

            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
        }
        create!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        name = eventName!!.text.toString().trim { it <= ' ' }
        eventDescription = description!!.text.toString().trim { it <= ' ' }
        location = createLocation!!.text.toString().trim { it <= ' ' }


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(eventDescription) && !TextUtils.isEmpty(location)) {


            val event = Event(name as String, eventDescription as String, location as String , dat as String, time as String)

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

                createLocation!!.setText(location_name)
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


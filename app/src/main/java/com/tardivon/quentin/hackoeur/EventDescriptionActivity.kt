package com.tardivon.quentin.hackoeur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event_description.*

class EventDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_description)
        val event = intent.getParcelableExtra<Event>("event")
        this.eventDate.text = event.date
        this.eventName.text = event.name
        this.eventLocation.text = event.location
        this.eventDescription.text = event.description
    }
}

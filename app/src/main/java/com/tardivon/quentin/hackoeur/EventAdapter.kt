package com.tardivon.quentin.hackoeur

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.event_item.view.*
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FileDownloadTask
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import com.google.firebase.storage.StorageReference




/**
 * Created by quentin on 11/13/17.
 */
class EventAdapter(val events: ArrayList<Event>, val itemClick: (Event) -> Unit): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bindEvent(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }


    class ViewHolder(view: View, val itemClick: (Event) -> Unit): RecyclerView.ViewHolder(view) {
        val storageRef = FirebaseStorage.getInstance().getReference();
        fun bindEvent(event: Event) {
            with(event) {

                itemView.eventName.text = "Event Name: " + event.name
                itemView.eventDate.text = "Date: "+ event.date
                itemView.eventLocation.text = "Location: "+ event.location
                if (event.imgId != null) {
                    val riversRef = storageRef.child("Photos/" + event.imgId)
                    val localFile = File.createTempFile("images", "jpg")
                    riversRef.getFile(localFile)
                            .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                                itemView.eventImg.setImageBitmap(bitmap)
                            }).addOnFailureListener(OnFailureListener {
                        // Handle failed download
                    })

                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

}
package com.tardivon.quentin.hackoeur

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.event_item.view.*

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
        fun bindEvent(event: Event) {
            with(event) {
                itemView.eventName.text = event.name
                itemView.eventDate.text = event.date
                itemView.eventLocation.text = event.location
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

}
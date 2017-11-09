package com.tardivon.quentin.hackoeur

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by quentin on 11/9/17.
 */

class RecyclerAdapter(private val eventList: List<EventModel>,
                      private val itemClick: (EventModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
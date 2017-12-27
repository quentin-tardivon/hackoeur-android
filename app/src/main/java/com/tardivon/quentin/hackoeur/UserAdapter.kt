package com.tardivon.quentin.hackoeur

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.friend_item.view.*

/**
 * Created by quentin on 11/13/17.
 */
class UserAdapter(val users: ArrayList<User>, val itemClick: (User) -> Unit): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bindEvent(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }


    class ViewHolder(view: View, val itemClick: (User) -> Unit): RecyclerView.ViewHolder(view) {
        fun bindEvent(user: User) {
            with(user) {
                itemView.friendEmail.text = user.email
                itemView.friendId.text = user.uid
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

}
package com.tardivon.quentin.hackoeur


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_friend_list.*


/**
 * A simple [Fragment] subclass.
 */
class FriendListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_friend_list, container, false)
        createFirebaseListener()
        return view
    }

    private fun createFirebaseListener() {
        val postListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val toReturn: ArrayList<User> = ArrayList()

                for (data in dataSnapshot.children) {
                    val friendData = data.getValue(User::class.java)

                    val friend = friendData?.let { it }?: continue

                    toReturn.add(friend)
                }

                setupAdapter(toReturn)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //log error
            }
        }
        (activity as MainActivity).databaseReference?.child("Users")?.addValueEventListener(postListener)
    }

    private fun setupAdapter(data: ArrayList<User>){
        val linearLayoutManager = LinearLayoutManager(this.activity)
        friendListRecyclerView.layoutManager = linearLayoutManager
        friendListRecyclerView.adapter = UserAdapter(data) {
            val intent: Intent = Intent(this.context, EventDescriptionActivity::class.java)
        }

        //scroll to bottom
        friendListRecyclerView.scrollToPosition(data.size - 1)
    }

}

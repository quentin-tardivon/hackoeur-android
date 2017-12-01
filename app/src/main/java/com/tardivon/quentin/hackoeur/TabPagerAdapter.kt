package com.tardivon.quentin.hackoeur


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by quentin on 12/1/17.
 */
class TabPagerAdapter(fm: FragmentManager, private var tabCount: Int): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return EventListFragment()
            1 -> return EventListFragment()
            2 -> return EventListFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}
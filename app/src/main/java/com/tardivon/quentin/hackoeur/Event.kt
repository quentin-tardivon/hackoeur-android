package com.tardivon.quentin.hackoeur

import android.os.Parcel
import android.os.Parcelable
/**
 * Created by quentin on 11/13/17.
 */
class Event() : Parcelable {

    var name: String? =null
        internal set
    var description: String?=null
        internal set
    var location: String? =null
        internal set
    var date: String? = null
        internal set
    var time: String? = null
    internal set
    var locationGPS: LatLng? = null
        internal set
    var registeredUsers: MutableList<String>? =null
            internal set
    var imgId: String? =null
        internal set
    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        description = parcel.readString()
        location = parcel.readString()
        date = parcel.readString()
        time = parcel.readString()
        imgId = parcel.readString()
    }

    constructor(name: String, description: String, location: String, date: String, time: String) : this() {
        this.name = name
        this.description = description
        this.location = location
        this.date = date
        this.time = time
    }

    constructor(name: String, description: String, location: String, date: String, time: String, locationGPS: LatLng) : this() {
        this.name = name
        this.description = description
        this.location = location
        this.date = date
        this.time = time
        this.locationGPS = locationGPS
    }

    constructor(name: String, description: String, location: String, date: String, time: String, locationGPS: LatLng, UsersId: MutableList<String> ) : this() {
        this.name = name
        this.description = description
        this.location = location
        this.date = date
        this.time = time
        this.locationGPS = locationGPS
        this.registeredUsers = UsersId
    }

    constructor(name: String, description: String, location: String, date: String, time: String, locationGPS: LatLng, UsersId: MutableList<String>, imgId: String) : this() {
        this.name = name
        this.description = description
        this.location = location
        this.date = date
        this.time = time
        this.locationGPS = locationGPS
        this.registeredUsers = UsersId
        this.imgId = imgId
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(location)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(imgId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }


}
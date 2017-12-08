package com.tardivon.quentin.hackoeur

/**
 * Created by quentin on 11/13/17.
 */
class Event {

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

    constructor(name: String, description: String, location: String, date: String,time: String) {
        this.name = name
        this.description = description
        this.location = location
        this.date = date
        this.time = time
    }

}
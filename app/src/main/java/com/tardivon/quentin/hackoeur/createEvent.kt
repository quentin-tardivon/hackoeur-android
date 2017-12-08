package com.tardivon.quentin.hackoeur

/**
 * Created by Ragul.S on 23-Nov-17.
 */

class createEvent {
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

    constructor() {

    }

    constructor(name: String, description: String, location: String, date: String,time: String) {
        this.name = name
        this.description = description
        this.location = location
        this.date = date
        this.time = time
    }

}

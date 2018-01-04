/**
 * Created by Admin on 08-12-2017.
 */

package com.tardivon.quentin.hackoeur

/**
 * Created by Admin on 01-12-2017.
 */

class User() {
    var email: String? = null
        internal set
    var uid: String? = null
        internal set
    var eventList: MutableList<String>? =null
        internal set
    constructor(email: String, uid: String): this() {
        this.email = email
        this.uid = uid
    }
    constructor(email: String, uid: String,eventlist: MutableList<String>): this() {
        this.email = email
        this.uid = uid
        this.eventList = eventlist
    }

}



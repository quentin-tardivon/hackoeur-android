package com.tardivon.quentin.hackoeur

/**
 * Created by quentin on 11/13/17.
 */
class Event {

    var name: String? = null
    var date: String? = null
    var location: String? = null

    constructor()

    constructor(name: String, date: String, location: String) {
        this.name = name
        this.date = date
        this.location = location
    }

}
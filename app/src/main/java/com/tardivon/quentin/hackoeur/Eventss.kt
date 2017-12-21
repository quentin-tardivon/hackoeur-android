package com.tardivon.quentin.hackoeur

/**
 * Created by Admin on 23-11-2017.
 */

/**
 * Created by Admin on 23-11-2017.
 */

class Eventss {
    internal var eventId: String?=null
    internal var eventname: String? =null
    internal var eventdescription: String? =null
    internal var eventlocation: String? =null
    internal var eventImage: String? =null
    internal var eventhour: String? =null
    internal var eventmin: String? =null
    internal var eventday: String? =null
    internal var eventmonth: String? =null
    internal var eventyear: String? =null
    internal var count: String? =null
    internal var clientId: String? =null
    internal var clients: String? =null

    constructor() {

    }

    constructor(clientId: String, clients: String, count: String, eventId: String, eventImage: String, eventday: String, eventdescription: String, eventhour: String, eventlocation: String, eventmin: String, eventmonth: String, eventname: String, eventyear: String) {
        this.eventId = eventId
        this.eventname = eventname
        this.eventdescription = eventdescription
        this.eventlocation = eventlocation
        this.eventImage = eventImage
        this.eventhour = eventhour
        this.eventmin = eventmin
        this.eventday = eventday
        this.eventmonth = eventmonth
        this.eventyear = eventyear
        this.count = count
        this.clientId = clientId
        this.clients = clients
    }


}




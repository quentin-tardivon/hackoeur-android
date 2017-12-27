/**
 * Created by Admin on 08-12-2017.
 */

package com.tardivon.quentin.hackoeur

/**
 * Created by Admin on 01-12-2017.
 */

class Clients {
    var clientId: String? =null
    var clientEmail: String? =null
    var events: String? =null

    constructor() {

    }

    constructor(clientId: String, clientEmail: String, events: String) {
        this.clientId = clientId
        this.clientEmail = clientEmail
        this.events = events
    }


}

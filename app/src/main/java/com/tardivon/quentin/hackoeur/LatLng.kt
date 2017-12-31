package com.tardivon.quentin.hackoeur

import com.google.android.gms.maps.model.LatLng

/**
 * Created by quentin on 12/31/17.
 */
class LatLng {
    var lat: Double? = null
    var lng: Double? = null

    constructor() {
        this.lat = 0.0
        this.lng = 0.0
    }

    constructor(lat: Double, lng: Double) {
        this.lat = lat
        this.lng = lng
    }

    constructor(latlng: LatLng) {
        this.lat = latlng.latitude
        this.lng = latlng.longitude
    }
}
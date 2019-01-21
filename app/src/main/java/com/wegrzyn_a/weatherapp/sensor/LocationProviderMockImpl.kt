package com.wegrzyn_a.weatherapp.sensor

import com.wegrzyn_a.weatherapp.data.model.LatLng

class LocationProviderMockImpl(val lat: Double, val long: Double) : LocationProvider {
    override fun getLatLng(onSuccess: (LatLng) -> Unit) {
        onSuccess.invoke(LatLng(lat,long))
    }
}
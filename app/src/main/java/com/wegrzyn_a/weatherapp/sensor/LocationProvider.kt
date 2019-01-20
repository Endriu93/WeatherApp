package com.wegrzyn_a.weatherapp.sensor

import com.wegrzyn_a.weatherapp.data.model.LatLng

interface LocationProvider {
    fun getLatLng(): LatLng
}
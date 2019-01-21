package com.wegrzyn_a.weatherapp.sensor

import com.wegrzyn_a.weatherapp.data.model.LatLng

interface LocationProvider {

    @Throws(PermissionNotGrantedException::class)
    fun getLatLng(onSuccess: (LatLng) -> Unit)
}
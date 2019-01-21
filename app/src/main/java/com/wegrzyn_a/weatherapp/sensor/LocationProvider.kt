package com.wegrzyn_a.weatherapp.sensor

import com.wegrzyn_a.weatherapp.data.model.LatLng
import io.reactivex.Single

interface LocationProvider {

    @Throws(PermissionNotGrantedException::class)
    fun getLatLng(): Single<LatLng>
}
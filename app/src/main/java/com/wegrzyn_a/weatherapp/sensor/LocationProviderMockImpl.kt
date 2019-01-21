package com.wegrzyn_a.weatherapp.sensor

import com.wegrzyn_a.weatherapp.data.model.LatLng
import io.reactivex.Single

class LocationProviderMockImpl(val lat: Double, val long: Double) : LocationProvider {
    override fun getLatLng(): Single<LatLng> = Single.just(LatLng(lat, long))
}
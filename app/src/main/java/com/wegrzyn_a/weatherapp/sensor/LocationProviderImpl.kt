package com.wegrzyn_a.weatherapp.sensor

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.wegrzyn_a.weatherapp.data.model.LatLng

class LocationProviderImpl(val context: Context) : LocationProvider {

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun getLatLng(onSuccess: (LatLng) -> Unit) {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        onSuccess.invoke(
                            LatLng(
                                location.latitude,
                                location.longitude
                            )
                        )
                    }
                }
        } catch (e: SecurityException) {
            throw PermissionNotGrantedException()
        }
    }
}
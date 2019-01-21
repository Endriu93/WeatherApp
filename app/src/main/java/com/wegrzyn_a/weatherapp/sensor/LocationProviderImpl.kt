package com.wegrzyn_a.weatherapp.sensor

import android.content.Context
import com.wegrzyn_a.weatherapp.data.model.LatLng
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider


class LocationProviderImpl(val context: Context) : LocationProvider {

    override fun getLatLng(): Single<LatLng> {
        val locationProvider = ReactiveLocationProvider(context)
        try {
            return locationProvider.lastKnownLocation.map { LatLng(it.latitude, it.longitude) }.observeOn(Schedulers.io())
                .singleOrError()
        } catch (e: SecurityException) {
            throw PermissionNotGrantedException()
        }
    }
}
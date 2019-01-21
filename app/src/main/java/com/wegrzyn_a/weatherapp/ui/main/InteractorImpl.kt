package com.wegrzyn_a.weatherapp.ui.main

import android.util.Log
import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import io.reactivex.Scheduler

class InteractorImpl(
    val dataSource: DataSource,
    val locationProvider: LocationProvider,
    val subscribeScheduler: Scheduler,
    val observeScheduler: Scheduler
) : MVP.Interactor {

    override fun getTemps(
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        locationProvider.getLatLng { latlng ->
            dataSource.getStations(latlng)
                .map { it.get(0).woeid }
                .flatMap { dataSource.getMeasurements(it) }
                .map { measurement -> measurement.consolidated_weather.map { it.the_temp } }
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe(
                    { onSuccess.invoke(it); println("OK");  },
                    { onError.invoke(it.message ?: ""); println("ERROR"); Log.d("Interactor:getTemps", it.toString()); })
        }

    }

    override fun unSubscribe() {
    }
}
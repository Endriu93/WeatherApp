package com.wegrzyn_a.weatherapp.ui.main

import android.util.Log
import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import io.reactivex.Scheduler
import io.reactivex.Single

class InteractorImpl(
    val dataSource: DataSource,
    val locationProvider: LocationProvider,
    val subscribeScheduler: Scheduler,
    val observeScheduler: Scheduler
) : MVP.Interactor {

    override fun getTempsForNextDays(): Single<List<String>> = locationProvider.getLatLng()
        .flatMap { dataSource.getStations(it) }
        .map { it.get(0).woeid }
        .flatMap { dataSource.getMeasurements(it) }
        .map { measurement -> measurement.consolidated_weather.map { it.the_temp } }
        .subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)

    override fun unSubscribe() {}
}



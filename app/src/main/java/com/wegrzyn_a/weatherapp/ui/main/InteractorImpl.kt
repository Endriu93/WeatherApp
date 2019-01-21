package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import io.reactivex.Scheduler
import io.reactivex.Single

class InteractorImpl(
    val dataSource: DataSource,
    val locationProvider: LocationProvider,
    val subscribeScheduler: Scheduler,
    val observeScheduler: Scheduler
) : MVP.Interactor {

    override fun getTempsForNextDays(): Single<List<Weather>> = locationProvider.getLatLng()
        .flatMap { dataSource.getStations(it) }
        .map { it.get(0).woeid }
        .flatMap { dataSource.getMeasurements(it) }
        .map { measurement -> measurement.consolidated_weather }
        .subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)

    override fun unSubscribe() {}
}



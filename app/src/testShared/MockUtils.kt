package com.wegrzyn_a.weatherapp

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import io.reactivex.Single
import org.mockito.Mockito

fun mockLocation(locationProvider: LocationProvider, long: Double, latt: Double) {
    Mockito.`when`(locationProvider.getLattLong()).thenReturn(Pair(latt, long))
}

fun mockDataSourceStations(dataSource: DataSource, latt: Double, long: Double, stations: List<Station>) {
    Mockito.`when`(dataSource.getStations(latt, long)).thenReturn(
        Single.just(
            stations
        )
    )
}

fun mockDataSourceMeasurement(dataSource: DataSource, woeid: Int, measurement: Measurement) {
    Mockito.`when`(dataSource.getMeasurements(woeid)).thenReturn(
        Single.just(
            measurement
        )
    )
}
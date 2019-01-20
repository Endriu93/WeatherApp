package com.wegrzyn_a.weatherapp.data

import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DataSource {

    @GET("/api/location/search/?lattlong={latt},{long}")
    fun getStations(@Path("latt") latt: Double, @Path("long") long: Double): Single<List<Station>>

    @GET("/api/location/{woeid}")
    fun getMeasurements(@Path("woeid") woeid: Int): Single<Measurement>
}
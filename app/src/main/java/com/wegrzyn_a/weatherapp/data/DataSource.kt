package com.wegrzyn_a.weatherapp.data

import com.wegrzyn_a.weatherapp.data.model.LatLng
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataSource {

    @GET("/api/location/search/")
    fun getStations(@Query("lattlong") latlng: LatLng): Single<List<Station>>

    @GET("/api/location/{woeid}")
    fun getMeasurements(@Path("woeid") woeid: Int): Single<Measurement>
}
package com.wegrzyn_a.weatherapp.data

import com.wegrzyn_a.weatherapp.data.model.LatLng
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DataSourceImpl(val baseUrl: String) : DataSource {

    var dataSource: DataSource

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dataSource = retrofit.create(DataSource::class.java)
    }

    override fun getStations(latlng: LatLng): Single<List<Station>> {
        return dataSource.getStations(latlng)
    }

    override fun getMeasurements(woeid: Int): Single<Measurement> {
        return dataSource.getMeasurements(woeid)
    }
}
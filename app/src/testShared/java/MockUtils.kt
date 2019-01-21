package com.wegrzyn_a.weatherapp

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.model.LatLng
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import com.wegrzyn_a.weatherapp.ui.main.MVP
import io.reactivex.Single
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.mockito.Mockito
import org.mockito.Mockito.`when`

fun mockLocation(locationProvider: LocationProvider, latt: Double, long: Double) {
    `when`(locationProvider.getLatLng()).thenReturn(Single.just(LatLng(latt, long)))
}

fun mockDataSourceStations(
    dataSource: DataSource,
    latt: Double,
    long: Double,
    stations: List<Station>
) {
    Mockito.`when`(dataSource.getStations(LatLng(latt,long))).thenReturn(
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

fun mockInteractorGetTemps(interactor: MVP.Interactor, temps: List<String>) {
    `when`(interactor.getTempsForNextDays()).thenReturn(Single.just(temps))
}

fun mockWebServer(mockServer: MockWebServer, pathToFileList: List<Pair<String, String>>) {
    mockServer.setDispatcher(object : Dispatcher() {
        override fun dispatch(request: RecordedRequest?): MockResponse {
            val matchingPath =
                pathToFileList.filter { request?.getPath()?.replace("%2C",",").equals(it.first) }.firstOrNull()

            if (matchingPath != null)
                return MockResponse().setResponseCode(200).setBody(
                    getJson(matchingPath.second)
                )
            else
                return MockResponse().setResponseCode(400)
        }
    })
}


